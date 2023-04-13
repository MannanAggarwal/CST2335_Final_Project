/**
 * @author Mannan
 */
package algonquin.cst2335.cst2335_final_project.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

 //A model class that represents a kitten image object.

@Entity
public class KittenImage {

    /**
     *  The unique identifier for the kitten image.
     */
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate=true)
    public int id;
    /**
     *  The width of the kitten image.
     */
    @ColumnInfo(name="width")
    protected String width;
    /**
     *  The height of the kitten image.
     */
    @ColumnInfo(name="height")
    protected String height;
    /**
     *  The date the kitten image was created.
     */
    @ColumnInfo(name="date")
    protected String date;
    /**
     *  The path to the image file for the kitten image.
     */
    @ColumnInfo(name="imagePath")
    protected String imagePath;
    /**
     *  Constructor for creating a new KittenImage object.
     *  @param width The width of the kitten image.
     *  @param height The height of the kitten image.
     *  @param date The date the kitten image was created.
     *  @param imagePath The path to the image file for the kitten image.
     */
    public KittenImage(String width, String height, String date, String imagePath) {
        this.width = width;
        this.height = height;
        this.date = date;
        this.imagePath = imagePath;
    }
    /**
     *  Returns the unique identifier for the kitten image.
     *  @return The unique identifier for the kitten image.
     */
    public int getId() {
        return id;
    }
    /**
     *  Returns the width of the kitten image.
     *  @return The width of the kitten image.
     */
    public String getWidth() {
        return width;
    }
    /**
     *  Returns the height of the kitten image.
     *  @return The height of the kitten image.
     */
    public String getHeight() {
        return height;
    }
    /**
     *  Returns the date the kitten image was created.
     *  @return The date the kitten image was created.
     */
    public String getDate() {
        return date;
    }
    /**
     *  Returns the path to the image file for the kitten image.
     *  @return The path to the image file for the kitten image.
     */
    public String getImagePath() {
        return imagePath;
    }
}