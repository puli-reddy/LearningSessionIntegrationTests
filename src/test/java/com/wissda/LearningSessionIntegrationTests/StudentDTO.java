package com.wissda.LearningSessionIntegrationTests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String studentId;
    private String name;
    private String email;
    private Address address;
}
