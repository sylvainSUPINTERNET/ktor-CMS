# Ktor - Mini CMS (Kotlin)

### Get started 
<ul>
    <li>Build project with gradlew</li>
    <li>Import database with the dump file (CMS.sql contains admin user for test admin interface)</li>
    <li>Rename <code>application.conf.dist</code> and configure the <code>application.conf</code> with your MySQL credentials (./src/main/resources)</li>
</ul>

### Admin  (Important)

__Make sure you imported the dump CMS.sql__ for admin features
```
    email: admin@admin.fr
    password: admin
```

(Note : password using BCrypt -> https://github.com/AndreasVolkmann/realworld-kotlin-ktor/blob/master/src/main/kotlin/me/avo/realworld/kotlin/ktor/auth/BcryptHasher.kt)

### Features

__*User (Not logged in)*__
<ul>
<li>
    Home page
    <ul>
        <li>
            List articles
        </li>
                <li>
                   Authentication form (admin)
                </li>
    </ul>
</li>
<li>
    Article page 
    <ul>
        <li>
            Add comment
        </li>
                <li>
                    List comments
                </li>
    </ul>
</li>
</ul>


__*ADMIN (logged in)*__
<ul>
<li>
    Home page
    <ul>
        <li>
            List articles
        </li>
                <li>
                   Authentication form (admin)
                </li>
                                <li>
                                   Logout
                                </li>
    </ul>
</li>
<li>
    Article page 
    <ul>
        <li>
            Add comment
        </li>
                <li>
                    List comments
                </li>
                <li>
                    Delete comments
                </li>
                <li>
                    Delete article (comments cascade)
                </li>
    </ul>
</li>
</ul>

__*BONUS*__
    <ul>
        <li>
            BCrypt for hash password admin
        </li>
         <li>
            application.conf for database settings (MySQL)
         </li>
   </ul>


### 


