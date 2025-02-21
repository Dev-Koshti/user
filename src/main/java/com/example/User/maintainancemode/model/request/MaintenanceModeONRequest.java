package com.example.User.maintainancemode.model.request;

import com.example.User.api.common.commonrequest.CommonAPIDataRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class MaintenanceModeONRequest extends CommonAPIDataRequest {

    private Boolean in_maintenance = true;

    public boolean checkBadRequest() {
        return StringUtils.isEmpty(this.getCompany_id());
    }
}
