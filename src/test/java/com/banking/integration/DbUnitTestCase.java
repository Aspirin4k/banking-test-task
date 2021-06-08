package com.banking.integration;

import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(DBUnitExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
abstract public class DbUnitTestCase {
}
