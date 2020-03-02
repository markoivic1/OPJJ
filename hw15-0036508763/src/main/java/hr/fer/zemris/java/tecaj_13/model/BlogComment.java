package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class represents table 'blog_comments'.
 * Comments are mapped to set blog entry.
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * Auto generated id
	 */
	private Long id;
	/**
	 * Owner of this comment
	 */
	private BlogEntry blogEntry;
	/**
	 * Email of a person who wrote this comment.
	 */
	private String usersEMail;
	/**
	 * Message
	 */
	private String message;
	/**
	 * Date at which this was created
	 */
	private Date postedOn;

	/**
	 * Gets id
	 * @return Returns ID
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets id
	 * @param id new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Blog entry which is mapped to this comment.
	 * @return Returns blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Sets blog entry.
	 * @param blogEntry new blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Gets user mail
	 * @return Returns user mail
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets user mail
	 * @param usersEMail new user mail.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Gets message.
	 * @return Returns message.
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets message.
	 * @param message Returns message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets date at which this was posted on.
	 * @return Returns date.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets date.
	 * @param postedOn new date
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	/**
	 * Hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Proper equals method
	 * @param obj Other object
	 * @return Returns true if they are of the same type and if their ids are the same, otherwise returns false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}