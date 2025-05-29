package org.csf.cat.glue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.assertj.core.api.Assertions;
import org.csf.cat.CatApplication;
import org.csf.cat.dao.CatDao;
import org.csf.cat.model.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@CucumberContextConfiguration
@SpringBootTest(classes = CatApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CatSteps {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CatDao catDao;

    private Cat catUpdated;

    @Given("^An existing cat with identifier ([a-z]|[A-Z]|[\\d])+ and a current number of votes equals to (\\d+)$")
    public void givenExistingCatWithVotes (String id, int votes) {
        Cat cat = Cat.builder().id(id).voteCount(votes).imageUrl("http://foo.png").build();
        catDao.save(cat);
    }

    @When("^A end-user votes for cat with identifier ([a-z]|[A-Z]|[\\d])+$")
    public void whenVoteForCat(String id) {
        restTemplate.postForEntity("/cats/" + id + "/vote", null, Void.class);
        catUpdated = catDao.findById(id).orElseThrow();
    }

    @Then("^Total votes for cat is equal to (\\d+)$")
    public void thenVoteCountIs (int expectedVotes) {
        Assertions.assertThat(expectedVotes).isEqualTo(catUpdated.getVoteCount());
    }
}
