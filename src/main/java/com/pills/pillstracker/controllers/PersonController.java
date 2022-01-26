package com.pills.pillstracker.controllers;

import com.pills.pillstracker.exceptions.PersonAlreadyExistException;
import com.pills.pillstracker.metrics.PillsTrackerMetrics;
import com.pills.pillstracker.models.daos.Person;
import com.pills.pillstracker.models.dtos.PersonDto;
import com.pills.pillstracker.services.PersonService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Controller()
@RequestMapping("/person")
public class PersonController {

    private static final String PERSON_REGISTER_VIEW = "person/register";
    private static final String PERSON_LIST_VIEW = "/person/list";

    private final ModelMapper modelMapper;
    private final PersonService personService;
    private final MessageSource messages;
    private final MeterRegistry meterRegistry;

    public PersonController(ModelMapper modelMapper, PersonService personService, MessageSource messages,
                            MeterRegistry meterRegistry) {

        this.modelMapper = modelMapper;
        this.personService = personService;
        this.messages = messages;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/register")
    public String showRegistrationForm(WebRequest request, Model model) {

        PersonDto personDto = new PersonDto();
        model.addAttribute("person", personDto);
        return PERSON_REGISTER_VIEW;
    }

    @PostMapping("/register")
    public String registrationSubmit(@ModelAttribute("person") @Valid PersonDto personDto,
                                     BindingResult result, Model model) {

        log.debug("Registration request for Person: {}", personDto);

        if (result.hasErrors()) {
            return PERSON_REGISTER_VIEW;
        }
        model.addAttribute("person", personDto);
        try {
            Person person = convertPersonToEntity(personDto);
            personService.registerNewPerson(person);
        } catch (PersonAlreadyExistException e) {
            result.addError(getError(e));
            return PERSON_REGISTER_VIEW;
        }
        log.debug("New Person with name: {} {} registered", personDto.getFirstName(), personDto.getLastName());
        meterRegistry.counter(PillsTrackerMetrics.PT_NUM_SUCCESS_PERSON_REGISTRY).increment();

        return PERSON_LIST_VIEW;
    }

    @GetMapping("/list")
    public String getAllPersons(WebRequest request, Model model) {

        log.debug("List all persons request");
        Set<Person> people = personService.retrieveAllPersonsForUser();
        List<PersonDto> personDtos = people
            .stream()
            .map(this::convertPersonToDto)
            .collect(Collectors.toList());
        model.addAttribute("people", personDtos);
        return PERSON_LIST_VIEW;
    }

    private Person convertPersonToEntity(PersonDto personDto) {

        return modelMapper.map(personDto, Person.class);
    }

    private PersonDto convertPersonToDto(Person person) {

        return modelMapper.map(person, PersonDto.class);
    }

    private ObjectError getError(PersonAlreadyExistException e) {

        return new ObjectError("person", messages.getMessage(e.getMessageCode(), null, LocaleContextHolder.getLocale()));
    }

}
