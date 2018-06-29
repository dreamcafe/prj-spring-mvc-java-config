package org.dreamcafe.smjc.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GreetingInfo {
    @SerializedName("name")
    private String name;

    @SerializedName("datetime")
    private String localCurrentDatetime;
}
