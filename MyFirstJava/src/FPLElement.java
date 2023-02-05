import com.google.gson.annotations.SerializedName;

public class FPLElement {
    @SerializedName("id")
    public int Id;
    @SerializedName("first_name")
    public String FirstName;
    @SerializedName("second_name")
    public String SecondName;
    @SerializedName("team")
    public int Team;

    @SerializedName("minutes")
    public int Minutes;
    @SerializedName("expected_goals_per_90")
    public double XGPer90;
    @SerializedName("expected_assists_per_90")
    public double XAPer90;
}
