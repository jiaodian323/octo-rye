/**   
* @Title: ArticleDetail.java
* @Package com.justnd.octoryeserver.vo 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年1月27日 上午11:34:31  
*/
package com.justnd.octoryeclient.entity.recommond;

public class ArticleDetail {
	private int id;
	private String type;
	private String title;
	private String publishTime;
	private String content;
	private String headImage;
	private String tags;
	private String extract;
	private int pageviewCount;
	private int likeNum;
	private Author author;

	
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the author
	 */
	public Author getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}

	/**
	 * @return the publishTime
	 */
	public String getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTime
	 *            the publishTime to set
	 */
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the headImage
	 */
	public String getHeadImage() {
		return headImage;
	}

	/**
	 * @param headImage
	 *            the headImage to set
	 */
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the extract
	 */
	public String getExtract() {
		return extract;
	}

	/**
	 * @param extract the extract to set
	 */
	public void setExtract(String extract) {
		this.extract = extract;
	}

	/**
	 * @return the pageviewCount
	 */
	public int getPageviewCount() {
		return pageviewCount;
	}

	/**
	 * @param pageviewCount the pageviewCount to set
	 */
	public void setPageviewCount(int pageviewCount) {
		this.pageviewCount = pageviewCount;
	}

	/**
	 * @return the likes
	 */
	public int getLikeNum() {
		return likeNum;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikeNum(int likes) {
		this.likeNum = likes;
	}

	public static class Author {
        private int id;
	    private String name;
	    private String introduction;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }
    }
}
