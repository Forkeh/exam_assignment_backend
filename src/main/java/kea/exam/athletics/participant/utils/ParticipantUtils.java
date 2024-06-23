package kea.exam.athletics.participant.utils;

import kea.exam.athletics.enums.AgeGroup;

public class ParticipantUtils {

    public AgeGroup ageToAgeGroup(int age) {
        if (age >= 6 && age <= 9) {
            return AgeGroup.CHILD;
        } else if (age >= 10 && age <= 13) {
            return AgeGroup.YOUTH;
        } else if (age >= 14 && age <= 22) {
            return AgeGroup.JUNIOR;
        } else if (age >= 23 && age <= 40) {
            return AgeGroup.ADULT;
        } else {
            return AgeGroup.SENIOR;
        }
    }
}
