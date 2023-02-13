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
    @SerializedName("element_type")
    public int ElementType;
    @SerializedName("now_cost")
    public int NowCost;

    @SerializedName("minutes")
    public int Minutes;
    @SerializedName("expected_goals_per_90")
    public double XGPer90;
    @SerializedName("expected_assists_per_90")
    public double XAPer90;

    @SerializedName("saves_per_90")
    public double SavesPer90;
    @SerializedName("expected_goals_conceded_per_90")
    public double XGCPer90;

    public double ExpectedPointsPer90;
}
