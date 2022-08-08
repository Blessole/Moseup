package project.moseup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SecretStatus {
	PUBLIC("공공"), SECRET("비밀");
	
	private String secertStatus;

}
