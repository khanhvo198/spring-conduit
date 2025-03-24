✅ About the Project
- Conduit is a simple, full-featured API for a Medium-like blogging platform, built with Java Spring Boot, you can explore it right [there](https://realworld-docs.netlify.app/).
The project supports user authentication, article management, comments, and social features like following authors and favoriting articles.
- It was built from scratch as a learning project to practice designing database relationships, writing clean API endpoints, and working with Spring Framework.

✨ Features
- User registration and authentication (JWT-based)

- Create, update, delete, and retrieve articles

- Slug-based article URL handling

- Follow/unfollow authors

- Like/unlike articles

- Pagination and filtering for article listings

- Add and delete comments on articles

- View profiles of other users


📬 API Endpoints
- POST /api/users — Register

- POST /api/users/login — Login

- GET /api/articles — Get all articles

- POST /api/articles — Create article

- POST /api/profiles/{username}/follow — Follow user

- POST /api/articles/{slug}/favorite — Favorite article
