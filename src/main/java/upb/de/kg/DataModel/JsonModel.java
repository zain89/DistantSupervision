package upb.de.kg.DataModel;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_id",
        "Source",
        "SourceLabel",
        "Source Position",
        "Sentence",
        "Sentence Portion",
        "Target Value",
        "Target Label",
        "Target Position"
})
public class JsonModel {

    @JsonProperty("_id")
    private UUID _id;
    @JsonProperty("Source")
    private String source;
    @JsonProperty("SourceLabel")
    private String sourceLabel;
    @JsonProperty("Source Position")
    private Integer sourcePosition;
    @JsonProperty("Sentence")
    private String sentence;
    @JsonProperty("Sentence Portion")
    private String sentencePortion;
    @JsonProperty("Target Value")
    private String targetValue;
    @JsonProperty("Target Label")
    private String targetLabel;
    @JsonProperty("Target Position")
    private Integer targetPosition;

    public JsonModel (){
        _id = UUID.randomUUID();
    }

    @JsonProperty("Source")
    public String getSource() {
        return source;
    }

    @JsonProperty("Source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("SourceLabel")
    public String getSourceLabel() {
        return sourceLabel;
    }

    @JsonProperty("SourceLabel")
    public void setSourceLabel(String sourceLabel) {
        this.sourceLabel = sourceLabel;
    }

    @JsonProperty("Source Position")
    public Integer getSourcePosition() {
        return sourcePosition;
    }

    @JsonProperty("Source Position")
    public void setSourcePosition(Integer sourcePosition) {
        this.sourcePosition = sourcePosition;
    }

    @JsonProperty("Sentence")
    public String getSentence() {
        return sentence;
    }

    @JsonProperty("Sentence")
    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    @JsonProperty("Sentence Portion")
    public String getSentencePortion() {
        return sentencePortion;
    }

    @JsonProperty("Sentence Portion")
    public void setSentencePortion(String sentencePortion) {
        this.sentencePortion = sentencePortion;
    }

    @JsonProperty("Target Value")
    public String getTargetValue() {
        return targetValue;
    }

    @JsonProperty("Target Value")
    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    @JsonProperty("Target Label")
    public String getTargetLabel() {
        return targetLabel;
    }

    @JsonProperty("Target Label")
    public void setTargetLabel(String targetLabel) {
        this.targetLabel = targetLabel;
    }

    @JsonProperty("Target Position")
    public Integer getTargetPosition() {
        return targetPosition;
    }

    @JsonProperty("Target Position")
    public void setTargetPosition(Integer targetPosition) {
        this.targetPosition = targetPosition;
    }



}