package resourceCRUD;

import java.io.IOException;

/**
 * Created by robertgu on 4/19/16.
 */
public interface ResourceCRUD {

    public void create(String location, String textToAdd) throws IOException;
    public String read(String location) throws IOException;
    public void update(String location, String updatingText);
    public void delete(String location);

}
