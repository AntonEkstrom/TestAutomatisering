Feature: Register users at Mailchimp.com

  Scenario Outline: Create users at Mailchimp.com
    Given I open the browser
    Given I enter an "<email>" address
    And I type an "<userName>" and a "<password>"
    Then I click on the sign-up button and the user is "<created>"

    Examples:
      | email                 | userName     | password     | created |
      | Antontavla@tavlor.se  | TavelAnton   | Tavelram321! | success |
      | Antontavla@tavlor.se  | Lars         | Tavelram321! | fail    |
      | Antontavla@tavlor.se  | sixsixsix@   | Tavelram321? | failed  |
      | 666                   | TavelAnton   | Tavelram321? | failing |