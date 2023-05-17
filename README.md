# Task-blog
Restful API Blog

# Description API Application
- database H2
- jdk minimum 17
- Spring boot version 3
- Include Authentication and Authorization
- Include JPA and Hibernate
- Include JWT Auth Barear 

# Test API Application 
- First you need register user for Authentication
    - (POST) localhost:8020/blog/api/main/v1/authentication/register-user
        - body :{
                    "username":"",
                    "email":"",
                    "password":""
                }
- Second you need login (expired token : 10 minutes) for access all API Blog 
    - (POST) localhost:8020/blog/api/main/v1/authentication/login
    - body :{
                "username":"",
                "password":""
            }

# URL API BLOG
- (POST) localhost:8020/blog/api/main/v1/blog/create-blog
    - Auth : 
        - type : Bearear Token
        - Token : 
    - body :{
                "blogName":"",
                "blogTitle":"",
                "blogBody":"",
                "blogAuthor":""
            }

- (PUT) localhost:8020/blog/api/main/v1/blog/update-blog/{blogId}
    - Auth : 
        - type : Bearear Token
        - Token : 
    - body :{
                "blogName":"",
                "blogTitle":"",
                "blogBody":"",
                "blogAuthor":""
            }

- (GET) localhost:8020/blog/api/main/v1/blog/get-blog/{blogId}
    - Auth : 
        - type : Bearear Token
        - Token : 

- (GET) localhost:8020/blog/api/main/v1/blog/search-blog
    - Auth : 
        - type : Bearear Token
        - Token : 
    - Param : 
        - pageNo=1 (min = 1)
        - pageSize=5 (min = 1)
        - blogId=
        - blogName=
        - blogTitle=

- (DELETE) localhost:8020/blog/api/main/v1/blog/delete-blog/{blogId}
    - Auth : 
        - type : Bearear Token
        - Token : 
