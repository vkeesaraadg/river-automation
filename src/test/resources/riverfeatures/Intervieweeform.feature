Feature: Interviewee form Actions

  #Interviewee form.
  @JIRA-Story
    
  Scenario Outline: Open and interact with the interviewee form
    Given On Login Screen I logged in to River
    And I open the "G-1256, Declaration for Interpreted USCIS Interview" form
    When I start the form
    And I click the "Next" button
    And I click the "Start" button
    And I scroll to the top of the page
    And I enter "A-232-323-232" into the Alien Registration Number field
    And I select "Albanian" from the Language combo box
    # And I click the "Continue" button