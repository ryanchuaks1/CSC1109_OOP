package Entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

public class Account {
    @DocumentId
    private String Id;

    @PropertyName("userId")
    private String userId;

    @PropertyName("animal")
    private String animal;
    public Account()
    {
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return Id;
    }

    public String getAnimal() {
        return animal;
    }
}
