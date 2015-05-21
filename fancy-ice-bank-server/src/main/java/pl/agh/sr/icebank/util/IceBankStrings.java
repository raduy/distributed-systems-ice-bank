package pl.agh.sr.icebank.util;

import Bank.PersonalData;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class IceBankStrings {
    private IceBankStrings() {
    }

    public static String toString(PersonalData personalData) {
        StringBuilder builder = new StringBuilder();
        return builder.append("PersonalData{")
                .append("Name: ")
                .append(personalData.firstName)
                .append(" Surname: ")
                .append(personalData.lastName)
                .append(" Nationality: ")
                .append(personalData.nationality)
                .append(" ID: ")
                .append(personalData.nationalIDNumber)
                .append("}")
                .toString();
    }
}
