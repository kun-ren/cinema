# Little Cinema

Little Cinema is a cinema booking and management application. A Spring Boot REST API serves three independent Vue applications: a customer storefront, an administration portal, and a worker/support portal. All three use the same MySQL database through the API.

## Applications and features

| Module | Audience | Implemented functionality |
| --- | --- | --- |
| `vue-app` | Customers | Registration/login, homepage posters and popular films, film search and genre/region filters, film details and ratings, screening selection, seat selection, cart, order/payment screens, profile and avatar updates, cinema messages, staff reviews, and activity registration. |
| `vue-admin` | Administrators and workers | Film CRUD, screening schedules and manual box-office updates, carousel posters, customer records, orders and cancellations, order issue handling, worker records and permissions, daily work updates, profile settings, and embedded Swagger documentation. |
| `vue-worker` | Support workers | Customer messages and replies, customer phone follow-up list, activity management, order information, staff reviews, and profile settings. The phone page displays contact information; it does not integrate a calling service. |
| `api` | All clients | Authentication, domain operations, database access, image storage, response/error handling, and Swagger API documentation. |

Payment is a demonstration workflow: the customer sees a static QR image and manually confirms payment. There is no payment-provider integration or server-side payment verification.

## Architecture

```text
Customer SPA        Admin SPA        Worker SPA
     \                 |                 /
      +---- Axios HTTP requests / JWT --+
                       |
               Spring Boot REST API
                       |
          Controllers -> Services -> Mappers
                       |                |
            Response view models      MySQL
```

The backend is a layered monolith. Each frontend is built and served separately; there is no shared frontend package, Vuex store, API gateway, or separate background service in this repository.

### Backend components

All Java code is under `api/src/main/java/com/movie/api`.

| Package | Responsibility |
| --- | --- |
| `controller` | HTTP routes under `/api`, request binding, and Swagger endpoint descriptions. |
| `service` / `service/impl` | Business operations, validations, timestamps, IDs, and assembly of related records. |
| `mapper` | MyBatis-Plus `BaseMapper` interfaces. Services use query wrappers for filtering and CRUD; no mapper XML is included. |
| `model/entity` | Database records mapped to `t_*` tables with `@TableName`. Lombok supplies accessors. |
| `model/dto` | Login request data (`username`, `password`, and `remember`). Most other writes accept entities directly. |
| `model/vo` | Composite responses for carts, orders, screenings, film reviews, messages, and active users. Services assemble these by loading related records. |
| `auth` / `config` | JWT request filter, stateless Spring Security, CORS, password encoder, and Swagger configuration. |
| `handler` / `annotation` | Standard JSON response envelope and exception handling. `@DisableBaseResponse` bypasses wrapping for image/other raw responses. |
| `constant` / `utils` | Role and status definitions, token handling, date formatting, and JSON response helpers. |

Normal responses have the shape below. Clients inspect `success` and the envelope's `code`; an error code in the body is not necessarily the HTTP status.

```json
{"code": 200, "success": true, "msg": null, "data": {}}
```

Uploads are an exception: `POST /api/upload` returns an image URL as a raw string, and `GET /api/upload?id=...` streams image bytes. Images are stored in `t_upload` and deduplicated by MD5, rather than written to a filesystem directory.

### Frontend components

Each Vue application follows the same organization:

```text
src/
  main.js             Vue startup, router, and English Element UI locale
  App.vue             Root component
  router/index.js     History-mode routes and lazy-loaded views
  components/layout/ Shared page shell, header, and sidebar or footer
  views/              Route pages, forms, tables, and page-specific state
  api/                Domain-specific HTTP helper functions
  utils/request.js    Axios instance and response notifications
  config.js           Backend API URL (and Swagger URL in admin)
  assets/             Images, icons, and styles
```

Customer pages use a header/footer layout, nested film detail/ticket routes, and a nested account area. Admin and worker pages use a sidebar/header layout with tables and modal forms. State lives in Vue components. Login tokens and account IDs are kept in `localStorage`; Axios sends the raw JWT in the `Authorization` header, without a `Bearer` prefix. Admin and worker routers check for a stored token before navigation. These browser checks do not replace API authorization.

### Data model and workflows

| Area | Tables and relationships |
| --- | --- |
| Identity | `t_user`, `t_admin`, and `t_worker`; `t_role.wid` associates permissions with workers. |
| Films and bookings | `t_arrangement.fid` references a film; `t_cart` and `t_order` carry `uid` and `aid` for the customer and screening. |
| Reviews and support | `t_film_evaluate` connects users and films; `t_worker_evaluate` connects users and workers; `t_leaving_message` holds customer messages and replies; `t_order_exception` records order issues. |
| Operations | `t_activity` and `t_registration` track activities and participants; `t_daily_work` stores work updates; `t_poster` stores homepage poster metadata; `t_upload` stores image bytes. |

These are relationships used by the code, not a claim that database foreign-key constraints are present: the repository does not include the original schema.

The booking flow is film -> screening -> up to four selected seats -> cart -> order -> payment confirmation. New seat strings are comma-separated, such as `1,2,12`; the backend also accepts the legacy non-numeric separators. Order creation checks existing screening orders for occupied seats and increases film popularity by the seat count. Payment attempts after 15 minutes mark an order as failed. Order statuses are `0` awaiting payment, `1` failed, `2` paid, and `3` canceled. The current seat lookup includes orders of every status, and booking/payment operations have no transactional seat-locking mechanism.

Activity registration validates the activity dates and prevents duplicate registration for a user, then increments the participant count. Film reviews prevent repeat ratings by the same user for the same film. Worker creation adds the base `ROLE_WORKER` permission, and the role API lets administrators manage additional permissions.

### API areas

| Prefix | Purpose |
| --- | --- |
| `/api/user`, `/api/admin`, `/api/worker` | Accounts and role-specific login |
| `/api/role` | Worker permissions and available roles |
| `/api/film`, `/api/arrangement`, `/api/fe` | Films, screenings, booked seats, and film reviews |
| `/api/cart`, `/api/order`, `/api/oe` | Cart, orders/payment, and order issues |
| `/api/lm`, `/api/we` | Cinema messages and worker reviews |
| `/api/activity`, `/api/registration` | Activities and participation |
| `/api/daily`, `/api/poster`, `/api/upload` | Work updates, carousel posters, and images |

The running API exposes Swagger at `http://localhost:8888/swagger-ui.html` and its specification at `/v2/api-docs`. Controller source is the reference for exact methods and parameters.

## Technology

- Backend: Java 8 target, Maven, Spring Boot 2.4.2, Spring Security, MyBatis-Plus 3.4.2, MySQL JDBC, JJWT 0.9.1, Springfox Swagger 2.9.2, Lombok, and Fastjson.
- Frontends: Vue 2, Vue Router 3, Element UI 2, Axios, and Vue CLI 4.5 with webpack 4. Each client has its own `package.json` and lockfile.
- Redis: dependency, TTL configuration, and cache annotations exist, but `@EnableCaching` is commented out in `ApiApplication`. Caching is not active by default.

## Local setup

### 1. Prepare the database and API

Use a JDK 8 installation and Maven. The application expects a MySQL database named `xiaoxiaomovie` at `localhost:3306`. **The original table-creation scripts, seed data, and initial administrator account are not included.** Obtain a compatible database or provision tables matching the entity mappings before testing database-backed features. MyBatis-Plus does not create these tables automatically.

Configuration lives in `api/src/main/resources/application.yml`. Override its development defaults with Spring environment variables such as `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, and `SPRING_DATASOURCE_PASSWORD`. For a local profile, an ignored `application-local.yml` can be placed alongside the main file and enabled with `SPRING_PROFILES_ACTIVE=local`.

From the repository root:

```sh
mvn -f api/pom.xml spring-boot:run
```

The default API address is `http://localhost:8888/api`. Customer accounts can be registered through the customer UI once the schema exists. Administrator records must be provisioned separately; this repository supplies no default administrator credentials. Worker accounts can be created through the admin portal.

### 2. Start the clients

Run each client in a separate terminal, with distinct ports to keep their `localStorage` sessions separate:

```sh
cd vue-app
npm ci
npm run serve -- --port 8080
```

```sh
cd vue-admin
npm ci
npm run serve -- --port 8081
```

```sh
cd vue-worker
npm ci
npm run serve -- --port 8082
```

All clients point to `http://localhost:8888/api` in their `src/config.js`. Update all three when moving the API. The admin config also contains the Swagger URL. The upload controller currently constructs `localhost` image URLs; that code must be adjusted for a remote deployment.

On Node 20, this webpack 4 toolchain is built with the OpenSSL compatibility option. Set it in the same terminal before running the frontend commands:

```powershell
$env:NODE_OPTIONS = '--openssl-legacy-provider'
```

For a POSIX shell, use `export NODE_OPTIONS=--openssl-legacy-provider`.

### 3. Build and verify

```sh
mvn -f api/pom.xml clean test
mvn -f api/pom.xml package
```

Run `npm run build` inside each frontend folder. Output goes to each client's `dist/`; Maven output goes to `api/target/`. A production static host must fall back to `index.html` for Vue Router history URLs.

There are no automated unit/integration tests or frontend lint/test scripts in the original project. The Maven `test` phase therefore checks compilation but runs no tests. For a functional smoke test with a prepared database, exercise customer login, film filtering, seat selection, cart/order creation, admin screening updates, worker message replies, and activity registration.

## English text and existing installations

UI labels, comments, API messages, and Swagger descriptions are English. Element UI's built-in controls also use English, and film dates are submitted as `yyyy-MM-dd`. Currency remains CNY and the booking phone field retains its existing 11-digit validation.

Stored category values are now English as well. For an existing database, review and run [the optional English-value migration](api/sql/migrate-english-values.sql) before using the updated clients. It converts known film genres/regions, user and worker genders, departments, support satisfaction levels, screening formats, and dates produced by the old film edit form. It is not automatically executed and does not create a schema. Back up the database first and stop clients during the migration. Legacy seat separators remain readable without a database migration. Film titles, descriptions, uploaded artwork, user names, and user-written messages are external content and are not automatically translated.

## Current implementation limits

The source is a prototype, with behavior worth understanding before extending it. API authorization is applied to selected methods, not consistently across every route. Customer and worker passwords use BCrypt, while administrator login compares the stored password directly. JWT signing uses a hard-coded development secret. Prices, IDs, and payment status come from client requests, and the payment flow is simulated. These details describe the current implementation; translation and repository cleanup do not change that business logic.

## Repository hygiene

The root `.gitignore` excludes dependencies, builds, coverage, logs, editor state, temporary files, and local configuration. Dependency lockfiles, required assets, and shared configuration templates remain versioned. Root documentation replaces the generated per-client READMEs. Unreferenced placeholder DTOs/components, an unused generated stylesheet, and unused images have been removed; the cart now displays each film's actual cover.
