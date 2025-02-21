package com.example.User.maintainancemode.model.request;

import com.example.User.api.common.commonrequest.CommonAPIDataRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MaintenanceModeOffRequest extends CommonAPIDataRequest {

    private Boolean in_maintenance = false;
    
}
