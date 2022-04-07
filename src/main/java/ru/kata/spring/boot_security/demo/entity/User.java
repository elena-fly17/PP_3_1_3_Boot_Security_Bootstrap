package ru.kata.spring.boot_security.demo.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
// класс user импл. и-с, чтобы мы могли получить из юзера данные, необходимые для работы
// спринг-секьюрити (юзернейм, пароль и список разрешений)
public class User implements UserDetails {
    // не делаем регистрацию для юзеров - только страницу для ввода логина и пароля
    // над полями убрала @Column с именами колонок - вдругхибер из-за этого не сработает?..

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private int age;
    private String phone;
    private String email;
    // добавленная колонка - в предыдущ. задаче нет
    private String username;
    // добавленная колонка - в предыдущ. задаче нет
    private String password;









    /*// СТАРЫЙ РАБОТАЮЩИЙ ВАРИАНТ - ДО ЧЕКБОКСОВ
    // обратить внимание, как будет подгружаться список ролей для юзера -
    // нужно исп. аннотацию фейтч или трансакшион (видео гикбрейнс)
    // для аннотации мэни-ту-мэни потом можно поэксперементировать - добавить к ней
    // cascade-type-all и см., не удалятся ли из БД роли, привязанные к этому юзеру
    // в БД название колонки в объединяющей табл. - roles_id, а здесь указано - role_id,
    // по идее таблицы долж. пересоздаться с нов. названиями, но вдруг этого не произойдет
    // @ManyToMany(cascade = CascadeType.ALL) // не уверена, что нужен каскад и этот его тип
    *//*@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH}) // а нужен ли каскад и конкретно detach?*//*
    @ManyToMany*//*(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)*//* // В СКОБКАХ НОВОЕ СЕГОДНЯ
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;
    // private Collection<Role> roles = new HashSet<>(); // НОВЫЙ ВАРИАНТ СЕГОДНЯ*/

    // НОВЫЙ ВАРИАНТ ДЛЯ ЧЕКБОКСОВ
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    // также пришлось изменить гетеры и сетеры для этого поля (дальше в классе)











    // убрала полн. конструктор, оставила только пустой - в некотор. туториалах нет
    // конструкторов, а в некоторых есть и пустые, и заполненные наполовину или полностью
    // возможно, у гикбрейнс нет конструкторов, т.к. что он непосредственно в коде
    // юзеров и роли не создает, а добавляет их сразу в БД сам в обход кода
    // индус доб. юзеров в БД через форму регистрации - поэтому, возможно, и обходится
    // без конструкторов
    // а вот мне, не исключаю, их все же придется создать
    // у Трегулова еще конструктор с параметрами, поэтому ниже создала - у гикбрейнс вроде нет
    public User() {
    }

    public User(int id, String name, String surname, int age, String phone,
                String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
    }

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }





    // ЭТО СТАРЫЕ ГЕТЕРЫ И СЕТЕРЫ, РАБОТАЮЩИЙ ВАРИАНТ - ДО ИЗМЕНЕНИЙ ПО ЧЕКБОКСАМ
    // эти гетер и сетер решила оставить - в принципе, не помешают
    // по сути, гетер дублирует метод getAuthorities
    /*public Collection<Role> getRoles() {
        return roles;
    }
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }*/
    // ЭТО НОВЫЕ ГЕТЕРЫ И СЕТЕРЫ - ДЛЯ ЧЕКБОКСОВ
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    // ОБРАТИ ВНИМАНИЕ - ВОЗМОЖНО И В МЕТОДЕ НИЖЕ НУЖНО ВНЕСТИ "КОЛЛЕКЦИОННЫЕ" ИЗМЕНЕНИЯ
    // здесь этот метод возвращает коллекцию ролей, а не разрешений, как ему вообще-то
    // положено - поэтому прямо в этом методе выполняем конвертацию
    // индус делает то же самое, только по-другому
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getAuthority()))
                .collect(Collectors.toList());
    }





    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
