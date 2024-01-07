package org.survey.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * User entity. JPA annotations are used to make this an entity. Lombok annotations are used to
 * reduce code. Named queries are provided for UserRepository. Table name is users because user is a
 * reserved word in sql.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = "password")
@EqualsAndHashCode(of = "username")
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("PMD.UnusedPrivateField")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NonNull
  private String username;
  @NonNull
  private String password;
  @NonNull
  private String email;
  @NonNull
  @Enumerated(EnumType.STRING)
  private Role role;
}
