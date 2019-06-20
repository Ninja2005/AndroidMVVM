package com.hqumath.androidmvvm.entity;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: CommitEntity
 * 作    者: Created by gyd
 * 创建时间: 2019/6/20 9:50
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class CommitEntity {

    /**
     * sha : 560f782f3d0749707cef76864633d8983a1e4796
     * node_id : MDY6Q29tbWl0MTg5NTU2NTY2OjU2MGY3ODJmM2QwNzQ5NzA3Y2VmNzY4NjQ2MzNkODk4M2ExZTQ3OTY=
     * commit : {"author":{"name":"gyd","email":"guoyadi@live.cn","date":"2019-06-17T03:08:43Z"},"committer":{"name
     * ":"gyd","email":"guoyadi@live.cn","date":"2019-06-17T03:08:43Z"},"message":"1.修改ui","tree":{"sha
     * ":"d393ba3f024e0723325f7584c86b6a9c5f2df584","url":"https://api.github
     * .com/repos/Ninja2005/AndroidMVVM/git/trees/d393ba3f024e0723325f7584c86b6a9c5f2df584"},"url":"https://api
     * .github.com/repos/Ninja2005/AndroidMVVM/git/commits/560f782f3d0749707cef76864633d8983a1e4796",
     * "comment_count":0,"verification":{"verified":false,"reason":"unsigned","signature":null,"payload":null}}
     * url : https://api.github.com/repos/Ninja2005/AndroidMVVM/commits/560f782f3d0749707cef76864633d8983a1e4796
     * html_url : https://github.com/Ninja2005/AndroidMVVM/commit/560f782f3d0749707cef76864633d8983a1e4796
     * comments_url : https://api.github
     * .com/repos/Ninja2005/AndroidMVVM/commits/560f782f3d0749707cef76864633d8983a1e4796/comments
     * author : null
     * committer : null
     * parents : [{"sha":"0b04760d10b45079895e8b61dfe33cda81394f06","url":"https://api.github
     * .com/repos/Ninja2005/AndroidMVVM/commits/0b04760d10b45079895e8b61dfe33cda81394f06","html_url":"https://github
     * .com/Ninja2005/AndroidMVVM/commit/0b04760d10b45079895e8b61dfe33cda81394f06"}]
     */

    private String sha;
    private String node_id;
    private CommitBean commit;
    private String url;
    private String html_url;
    private String comments_url;
    private Object author;
    private Object committer;
    private List<ParentsBean> parents;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public CommitBean getCommit() {
        return commit;
    }

    public void setCommit(CommitBean commit) {
        this.commit = commit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getComments_url() {
        return comments_url;
    }

    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }

    public Object getAuthor() {
        return author;
    }

    public void setAuthor(Object author) {
        this.author = author;
    }

    public Object getCommitter() {
        return committer;
    }

    public void setCommitter(Object committer) {
        this.committer = committer;
    }

    public List<ParentsBean> getParents() {
        return parents;
    }

    public void setParents(List<ParentsBean> parents) {
        this.parents = parents;
    }

    public static class CommitBean {
        /**
         * author : {"name":"gyd","email":"guoyadi@live.cn","date":"2019-06-17T03:08:43Z"}
         * committer : {"name":"gyd","email":"guoyadi@live.cn","date":"2019-06-17T03:08:43Z"}
         * message : 1.修改ui
         * tree : {"sha":"d393ba3f024e0723325f7584c86b6a9c5f2df584","url":"https://api.github
         * .com/repos/Ninja2005/AndroidMVVM/git/trees/d393ba3f024e0723325f7584c86b6a9c5f2df584"}
         * url : https://api.github.com/repos/Ninja2005/AndroidMVVM/git/commits/560f782f3d0749707cef76864633d8983a1e4796
         * comment_count : 0
         * verification : {"verified":false,"reason":"unsigned","signature":null,"payload":null}
         */

        private AuthorBean author;
        private CommitterBean committer;
        private String message;
        private TreeBean tree;
        private String url;
        private int comment_count;
        private VerificationBean verification;

        public AuthorBean getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBean author) {
            this.author = author;
        }

        public CommitterBean getCommitter() {
            return committer;
        }

        public void setCommitter(CommitterBean committer) {
            this.committer = committer;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public TreeBean getTree() {
            return tree;
        }

        public void setTree(TreeBean tree) {
            this.tree = tree;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public VerificationBean getVerification() {
            return verification;
        }

        public void setVerification(VerificationBean verification) {
            this.verification = verification;
        }

        public static class AuthorBean {
            /**
             * name : gyd
             * email : guoyadi@live.cn
             * date : 2019-06-17T03:08:43Z
             */

            private String name;
            private String email;
            private String date;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }

        public static class CommitterBean {
            /**
             * name : gyd
             * email : guoyadi@live.cn
             * date : 2019-06-17T03:08:43Z
             */

            private String name;
            private String email;
            private String date;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }

        public static class TreeBean {
            /**
             * sha : d393ba3f024e0723325f7584c86b6a9c5f2df584
             * url : https://api.github.com/repos/Ninja2005/AndroidMVVM/git/trees/d393ba3f024e0723325f7584c86b6a9c5f2df584
             */

            private String sha;
            private String url;

            public String getSha() {
                return sha;
            }

            public void setSha(String sha) {
                this.sha = sha;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class VerificationBean {
            /**
             * verified : false
             * reason : unsigned
             * signature : null
             * payload : null
             */

            private boolean verified;
            private String reason;
            private Object signature;
            private Object payload;

            public boolean isVerified() {
                return verified;
            }

            public void setVerified(boolean verified) {
                this.verified = verified;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public Object getSignature() {
                return signature;
            }

            public void setSignature(Object signature) {
                this.signature = signature;
            }

            public Object getPayload() {
                return payload;
            }

            public void setPayload(Object payload) {
                this.payload = payload;
            }
        }
    }

    public static class ParentsBean {
        /**
         * sha : 0b04760d10b45079895e8b61dfe33cda81394f06
         * url : https://api.github.com/repos/Ninja2005/AndroidMVVM/commits/0b04760d10b45079895e8b61dfe33cda81394f06
         * html_url : https://github.com/Ninja2005/AndroidMVVM/commit/0b04760d10b45079895e8b61dfe33cda81394f06
         */

        private String sha;
        private String url;
        private String html_url;

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }
    }
}
