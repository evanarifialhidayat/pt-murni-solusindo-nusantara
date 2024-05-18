package com.app.interview.murni.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Builder.Default;


@Entity
@Table(name = "avgmeanmodus" )
public class ListAvarageMeanModus {
	

	public ListAvarageMeanModus(String name,Integer score,String emotion,Calendar created) {
		super();
		this.name = name;
		this.score = score;
		this.emotion = emotion;
		this.created = created;
	}

    public ListAvarageMeanModus(String name,Double avg) {
		super();
		this.avg = avg;
		this.name = name;
	}

    public ListAvarageMeanModus(Integer no,String name,String created_string,Double avg) {
		super();
		this.avg = avg;
		this.name = name;
		this.created_string = created_string;
		this.no = no;
	}
    
    public ListAvarageMeanModus(Integer no,String created_string,String name,String emotion) {
		super();
		this.no = no;
		this.created_string = created_string;
		this.name = name;
		this.emotion = emotion;
	}
    
    
    public ListAvarageMeanModus(String name,String emotion,Integer rnk) {
		super();
		this.name = name;
		this.emotion = emotion;
		this.rnk = rnk;
	}



	public ListAvarageMeanModus(){}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    public Long id;
    
    @Column(name="name")
    @NotBlank(message = "Name cannot blank")
    @Size(max = 100, message = "Name should not be greater than 100 caracters")
	public String name ;
    
    @Column(name="score")
    @Range(min = 0)
	public Integer score;
    
    @Column(name="emotion")
    @NotBlank(message = "Emotion cannot blank")
    @Size(max = 100)
	public String emotion ;
    
    @Column(name="created")
    @Temporal(TemporalType.DATE)
    private Calendar created;
    
    @Transient
    private String created_string;
    
    @Transient
    private Double avg;
    
    @Transient
    private Integer rnk;
    
    @Transient
    private Integer no;
    
    

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Integer getRnk() {
		return rnk;
	}



	public void setRnk(Integer rnk) {
		this.rnk = rnk;
	}



	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public String getCreated_string() {
		return created_string;
	}

	public void setCreated_string(String created_string) {
		this.created_string = created_string;
	}

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Integer getScore() {
		return score;
	}



	public void setScore(Integer score) {
		this.score = score;
	}



	public String getEmotion() {
		return emotion;
	}



	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}



	public Calendar getCreated() {
		return created;
	}



	public void setCreated(Calendar created) {
		this.created = created;
	}



	@Override
	public String toString() {

				return "{\"recid\":"+       id 
				+ ", \"name\":\"" +         name 
				+ "\", \"score\":\"" +      score 
				+ "\", \"emotion\":\"" +  emotion
				+ "\", \"created_string\":\"" +  created_string
				+ "\", \"id\":\"" +  id
				+"\"}";
	}


	

    
}

