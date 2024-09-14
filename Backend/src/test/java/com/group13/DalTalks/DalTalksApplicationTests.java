package com.group13.DalTalks;

import com.group13.DalTalks.repository.UserRepository;
import com.group13.DalTalks.service.Implementations.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DalTalksApplicationTests {

	@Mock
	private UserRepository userRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@BeforeEach
	void setUp() {
		userServiceImpl = new UserServiceImpl();
	}

	@Test
	void contextLoads() {
	}
	
}
