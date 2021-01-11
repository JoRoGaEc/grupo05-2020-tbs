package com.sif.digestyc.ServiceTest;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sif.digestyc.Entity.Human;
import com.sif.digestyc.Repository.HumanRepository;

@SpringBootTest
public class InsercionUnoaunoTest {
	
	@Autowired
	private HumanRepository humanRepository;
	private Logger logger = LoggerFactory.getLogger(InsercionUnoaunoTest.class);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}	
	
	
	@Test
	@DisplayName("probar tiempo de insercion")
	public void insertar() {
		// TODO Auto-generated method stub		
        List<Human> persons = getPersonList(100000);

        Instant start = Instant.now();
        //action.invoke()
        logger.info("TIEMPO INICIAL " + start);
        for (Human p: persons) {  
            humanRepository.save(p);
        }
        Instant end = Instant.now();
        logger.info("\n\n\n\n--------------DURACION DE INSERCION " + formatDuration(Duration.between(start, end)));
	}
	
    public List<Human> getPersonList(int numPersons) {
        List<Human> persons = new ArrayList<>();

        for (int pos = 0; pos < numPersons; pos++) {
            Human p = new Human();
            p.setFirstName("Philipp");
            p.setLastName("Wagner");

            persons.add(p);
        }

        return persons;
    }
    public static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
            "%d:%02d:%02d",
            absSeconds / 3600,
            (absSeconds % 3600) / 60,
            absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
	

}
