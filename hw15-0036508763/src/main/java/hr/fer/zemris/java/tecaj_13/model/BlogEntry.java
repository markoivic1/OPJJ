package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;

@NamedQueries({
        @NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")
})
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
/**
 * Class which defines 'blog_entries' table.
 * It stores comments which are mapped on this.
 */
public class BlogEntry {
    /**
     * auto generated id
     */
    private Long id;
    /**
     * List of comments
     */
    private List<BlogComment> comments = new ArrayList<>();
    /**
     * Date at which blog entry was created
     */
    private Date createdAt;
    /**
     * Date at which blog entry was last modified.
     */
    private Date lastModifiedAt;
    /**
     * Title of a blog entry.
     */
    private String title;
    /**
     * Text of a blog entry
     */
    private String text;
    /**
     * {@link BlogUser} which created this blog entry.
     */
    private BlogUser creator;

    /**
     * Uses HttpServletRequest to fill needed data
     * @param req HttpServletRequest
     * @param creator Creator
     */
    public void fillFromHttpRequest(HttpServletRequest req, BlogUser creator) {
        title = req.getParameter("title");
        text = req.getParameter("text");
        createdAt = new Date();
        lastModifiedAt = createdAt;
    }

    /**
     * Checks if containing data is valid.
     * @return Returns true if they are valid, otherwise return false.
     */
    public boolean valid() {
        if (title == null ||
                text == null ||
                creator == null ||
                title.equals("") ||
                text.equals("")) {
            return false;
        }
        return true;
    }

    /**
     * Gets creator.
     * @return Returns creator.
     */
    @ManyToOne
    public BlogUser getCreator() {
        return creator;
    }

    /**
     * Sets creator
     * @param creator new creator.
     */
    public void setCreator(BlogUser creator) {
        this.creator = creator;
    }

    /**
     * Gets id
     * @return Returns id
     */
    @Id
    @GeneratedValue
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
     * Returns list of comments.
     * @return Returns list of comments.
     */
    @OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("postedOn")
    public List<BlogComment> getComments() {
        return comments;
    }

    /**
     * Sets new list of comments
     * @param comments
     */
    public void setComments(List<BlogComment> comments) {
        this.comments = comments;
    }

    /**
     * Gets date at which this blog entry was created.
     * @return Returns date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets date
     * @param createdAt new date
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the date this was last modified.
     * @return Returns date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * Sets the date at which this is modified.
     * @param lastModifiedAt new date
     */
    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Gets title
     * @return Returns title
     */
    @Column(length = 200, nullable = false)
    public String getTitle() {
        return title;
    }

    /**
     * Sets title
     * @param title new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets text
     * @return Returns text
     */
    @Column(length = 4096, nullable = false)
    public String getText() {
        return text;
    }

    /**
     * Sets text
     * @param text new text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * hash code
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * Proper equal method comparing ids.
     * @param obj Other object.
     * @return Returns true if objects are of the same type and their id is the same, otherwise return false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BlogEntry other = (BlogEntry) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}