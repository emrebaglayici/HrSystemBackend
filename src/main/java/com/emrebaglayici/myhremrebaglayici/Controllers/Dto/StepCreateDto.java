package com.emrebaglayici.myhremrebaglayici.Controllers.Dto;

import com.emrebaglayici.myhremrebaglayici.Entities.Step;
import lombok.Setter;

@Setter
public class StepCreateDto {
    private String name;
    private Long applicationId;
    private boolean result;
    private String notes;

    public Step toStep(){
        if (name.length()>0)
            this.name=name.toUpperCase();
        return Step.builder()
                .name(this.name)
                .applicationId(this.applicationId)
                .orderCount(1)
                .result(this.result)
                .notes(this.notes)
                .build();
    }
}
