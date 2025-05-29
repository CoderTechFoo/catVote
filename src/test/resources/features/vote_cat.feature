Feature: Vote for a cat

  Scenario: A end-user has voted for a cat
    Given An existing cat with identifier loveCat1 and a current number of votes equals to 5
    When A end-user votes for cat with identifier loveCat1
    Then Total votes for cat is equal to 6