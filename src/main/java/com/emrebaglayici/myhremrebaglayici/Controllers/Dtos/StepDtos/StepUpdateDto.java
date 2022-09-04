package com.emrebaglayici.myhremrebaglayici.Controllers.Dtos.StepDtos;

import com.emrebaglayici.myhremrebaglayici.Entities.Step;
import lombok.Setter;

@Setter
public class StepUpdateDto {
    private Integer id;
    private String name;
    private Long applicationId;
    private boolean result;
    private String notes;

    public Step toStep(){
        if (name.length()>0)
            this.name=name.toUpperCase();
        return Step.builder()
                .id(this.id)
                .name(this.name)
                .applicationId(this.applicationId)
                .orderCount(1)
                .result(this.result)
                .notes(this.notes)
                .build();
    }
}
