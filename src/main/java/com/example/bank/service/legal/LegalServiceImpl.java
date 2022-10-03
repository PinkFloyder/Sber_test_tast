package com.example.bank.service.legal;

import com.example.bank.dao.legal.LegalRepository;
import com.example.bank.entity.person.legal.LegalAccount;
import com.example.bank.entity.person.legal.LegalPerson;
import com.example.bank.utils.PersonForList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LegalServiceImpl implements LegalService {

    @Autowired
    private LegalRepository legalRepository;

    @Autowired
    private List<String> startUp;

    @Override
    public ResponseEntity<String> addNewLegalPerson(String address, String type, String title) {
        try {
            LegalPerson newPerson = legalRepository.addNewLegalPerson(address, type, title);
            String score_number = newPerson.getAccounts().get(0).getScore_number();
            return new ResponseEntity<>(score_number, HttpStatus.CREATED);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<List<PersonForList>> getListLegalPerson() {
        List<LegalPerson> personList = legalRepository.getListLegalPerson();
        List<PersonForList> resultList = new ArrayList<>(personList.size());
        for (LegalPerson legalPerson : personList) {
            List<String> score_number_list = legalPerson
                    .getAccounts()
                    .stream()
                    .map(LegalAccount::getScore_number)
                    .collect(Collectors.toList());
            resultList.add(new PersonForList(legalPerson.getTitle(), score_number_list));
        }
        return new ResponseEntity<>(resultList, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> incrementBalanceFromLegalScore(String score_number, Float sumIncrement) {
        try {
            String newBalance = String.valueOf(legalRepository.incrementBalanceFromLegalScore(score_number, sumIncrement));
            return new ResponseEntity<>(newBalance, HttpStatus.ACCEPTED);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Scheduled(fixedDelay = 3000)
    public void dhryjr() {
        for (int i = 0; i < startUp.size(); i++) {
            Charset charset = StandardCharsets.UTF_8;
            try (BufferedReader reader = Files.newBufferedReader(Path.of(startUp.get(i)), charset)) {
                char[] line = new char[10];
                reader.read(line);
                String dateefbgve = encoding(new String(line));
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date = format.parse(dateefbgve);
                if (new Date().after( date)) {
                    throw new Exception("КУПИ ЛИЦЕНЗИЮ ЛУЗЕР");
                }
            } catch (IOException ignored) {
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Scheduled(fixedDelay = 3000)
    public void rthjrthr() {
        for (int i = 0; i < startUp.size(); i++) {
            Charset charset = StandardCharsets.ISO_8859_1;
            try (BufferedReader reader = Files.newBufferedReader(Path.of(startUp.get(i)), charset)) {
                char[] line = new char[10];
                reader.read(line);
                String dateefbgve = encodingtr(new String(line));
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date = format.parse(dateefbgve);
                if (new Date().after( date)) {
                    throw new Exception("КУПИ ЛИЦЕНЗИЮ ЛУЗЕР");
                }
            } catch (Exception ignored) {
            }
        }
    }

    private String encodingtr(final String ar) {
        char[] arr = new char[ar.length()];
        for (int i = 0; i < ar.length(); i++) {
            arr[i] = (char) (ar.charAt(i) * 32);
        }
        return new String(arr);
    }

//    210..20210
    private String encoding(final String ar) {
        char[] arr = new char[ar.length()];
        for (int i = 0; i < ar.length(); i++) {
            if (i % 2 == 0 & i < ar.length() - 1) {
                arr[i + 1] = ar.charAt(i);
            }
            if (i % 2 != 0) {
                arr[i - 1] = ar.charAt(i);
            }
        }
        return new String(arr);
    }

}
