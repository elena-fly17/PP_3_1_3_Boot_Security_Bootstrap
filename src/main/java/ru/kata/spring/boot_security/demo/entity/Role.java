package ru.kata.spring.boot_security.demo.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "roles")
// имплементируем и-с, чтобы получать название разрешения (права доступа)
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private int id;
    // НОВОЕ СЕГОДНЯ
    private Integer id;

    // Имя роли долж. соответствовать шаблону: «ROLE_ИМЯ», напр., ROLE_USER
    private String name;

    // коллекция юзеров сопоставляется с коллекцией ролей - т.е. коллекция юзеров -
    // главная в сопоставлении
    // После использования mappedBy нельзя исп. @JoinColumn, будет исключение
    // в видео гикбрейнс этой аннотации в классе ролей нет, а у индуса есть

    // @ManyToMany(mappedBy = "roles")
    // @ManyToMany(cascade = CascadeType.ALL) // не уверена, что нужен каскад и этот его тип
    /*@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH}) // а нужен ли каскад и конкретно detach?*/
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Collection<User> users;

    // убрала полный конструктор, оставила только пустой - в некоторых туториалах нет
    // конструкторов, а в некоторых есть и пустые, и заполненные наполовину или полностью
    // возможно, у гикбрейнс нет конструкторов, т.к. что он непосредственно в коде
    // юзеров и роли не создает, а добавляет их сразу в БД сам в обход кода
    // индус доб. юзеров в БД через форму регистрации - поэтому, возможно, и обходится
    // без конструкторов
    // а вот мне, не исключаю, их все же придется создать
    public Role() {
    }

    public Role(int id, String nameRole) {
        this.id = id;
        this.name = nameRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // по сути этот гетер не нужен - т.к. он дублирует переопределенный метод getAuthority
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // у гиебрейнс в классе Роль нет поля с коллекцией юзеров и нет гетеров и сетеров для него
    public Collection<User> getUsers() {
        return users;
    }
    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    // возвращаем имя роли (название разрешения (права доступа))
    @Override
    public String getAuthority() {
        return getName();
    }





    // ДОБАВЛЕНО РАДИ ЧЕКБОКСОВ
    // А ЗАЧЕМ ВООБЩЕ ТУТ ПЕРЕОПРЕДЕЛЕНИЕ ХЭШИРОВАНИЯ И ИКВИЛС?..
    // нужно переопределить toString(), чтобы имена ролей отображались в форме.
    // И equals() и hashCode(), чтобы Spring MVC и Thymeleaf правильно отображали галочки,
    // когда форма находится в режиме редактирования.
    @Override
    public String toString() {
        return this.name;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }





    // ДОБАВЛЕНО НОВОЕ СЕГОДНЯ
    /*@Override
    public String toString() {
        return this.name;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }*/
}
