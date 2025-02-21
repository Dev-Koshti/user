package com.example.User.maintainancemode.model.request;

import com.example.User.api.common.commonrequest.CommonAPIDataRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class RetriveMaintenanceModeRequest extends CommonAPIDataRequest {

    public boolean checkBadRequest() {
        return StringUtils.isEmpty(this.getCompany_id());
    }
}
