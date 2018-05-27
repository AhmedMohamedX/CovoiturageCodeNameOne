/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp;

import java.util.Date;

/**
 *
 * @author AH Info
 */
public class comment {
    private int id;
    private String body;
    private String ancestors;
    private int depth;
    private int state;
    private Date created_at;
    private threadComment threadComment;
    private User membre_id;

    public User getMembre_id() {
        return membre_id;
    }

    public void setMembre_id(User membre_id) {
        this.membre_id = membre_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }



    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public threadComment getThreadComment() {
        return threadComment;
    }

    public void setThreadComment(threadComment threadComment) {
        this.threadComment = threadComment;
    }

    public comment(String body, String ancestors, int depth, int state, Date created_at, threadComment threadComment) {
        this.body = body;
        this.ancestors = ancestors;
        this.depth = depth;
        this.state = state;
        this.created_at = created_at;
        this.threadComment = threadComment;
    }

    public comment(String body, String ancestors, int depth, int state, Date created_at, threadComment threadComment, User membre_id) {
        this.body = body;
        this.ancestors = ancestors;
        this.depth = depth;
        this.state = state;
        this.created_at = created_at;
        this.threadComment = threadComment;
        this.membre_id = membre_id;
    }

  
 



    public comment() {
    }

    @Override
    public String toString() {
        return "comment{" + "id=" + id + ", body=" + body + ", ancestors=" + ancestors + ", depth=" + depth + ", state=" + state + ", thread=" + threadComment + '}';
    }
    
    
    
}
