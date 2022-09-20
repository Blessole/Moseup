package project.moseup.domain;

public enum MemberGender {
	MALE("남자"), FEMALE("여자");

	private final String description;

	MemberGender(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}
}
