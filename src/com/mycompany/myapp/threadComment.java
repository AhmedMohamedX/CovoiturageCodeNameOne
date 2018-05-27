/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author AH Info
 */
public class threadComment {
    private int id;
    private String permalink;
    private int is_commentable;
    private int num_comments;
    private Date last_comment_at;
    

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public int getIs_commentable() {
        return is_commentable;
    }

    public void setIs_commentable(int is_commentable) {
        this.is_commentable = is_commentable;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(int num_comments) {
        this.num_comments = num_comments;
    }

    public Date getLast_comment_at() {
        return last_comment_at;
    }

    public void setLast_comment_at(Date last_comment_at) {
        this.last_comment_at = last_comment_at;
    }

    public threadComment(String permalink, int is_commentable, int num_comments, Date last_comment_at) {
        this.permalink = permalink;
        this.is_commentable = is_commentable;
        this.num_comments = num_comments;
        this.last_comment_at = last_comment_at;
        
    }

   
 

    public threadComment() {
    }

    @Override
    public String toString() {
        return "thread{" + "id=" + id + ", permalink=" + permalink + ", is_commentable=" + is_commentable + ", num_comments=" + num_comments + ", last_comment_at=" + last_comment_at  + '}';
    }

    public threadComment(int id) {
        this.id = id;
    }

    public threadComment(int id, String permalink, int is_commentable, int num_comments, Date last_comment_at) {
        this.id = id;
        this.permalink = permalink;
        this.is_commentable = is_commentable;
        this.num_comments = num_comments;
        this.last_comment_at = last_comment_at;
    }

  
    
    
}
