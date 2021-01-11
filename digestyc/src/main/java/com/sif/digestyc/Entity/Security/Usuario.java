package com.sif.digestyc.Entity.Security;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.JoinColumn;

import com.sif.digestyc.Entity.LoadModule.Entrega;
import com.sif.digestyc.Entity.LoadModule.Institucion;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Listener.BitacoraListener;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;


@Entity
@EntityListeners(BitacoraListener.class)
@Table(name = "usuario", schema="seguridad")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "El nombre es obligatorio")
	@NotNull(message = "El nombre del usuario es obligatorio")
	@Length(min = 3, max =128, message = "El nombre de usuario debe tener mínimo 3 caracteres y menos de 128 caracteres")
	@Column(unique = false, length = 128, nullable = false)
	private String nombre;

	@NotEmpty(message = "El apellido es obligatorio")
	@NotNull(message = "El apellido del usuario es obligatorio")
	@Length(min = 3, max =128, message = "El apellido de usuario debe tener mínimo 3 caracteres y menos de 128 caracteres")
	@Column(unique = false, length = 128, nullable = false)
	private String apellido;

	@NotEmpty(message = "El username es obligatorio")
	@NotNull(message = "El username es obligatorio")
	@Length(min = 6, max =75, message = "El usuario debe tener mínimo 6 caracteres y menos de 75 caracteres")
	@Column(unique = true, length = 75, nullable = false)
	private String username;

	@NotEmpty(message = "La contraseña es obligatoria")
	@Column(length = 128, nullable = false)
	@NotNull(message = "La contraseña|Password es obligatoria")
	@Length(min = 8, message = "Debe contener mínimo 8 caracteres")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message="La contraseña debe tener al menos 8 caracteres, un número, una letra mayúscula y una minúscula")
	private String password;

	@NotEmpty(message = "El correo no debe estar vacío")
	@Column(unique = true,length = 64, nullable = false)
	@NotNull(message = "El correo es obligatorio")
	@Email(message = "Ingrese un correo valido")
	private String email;

	//@NotEmpty
	@NotNull
	@Column(nullable = false)
	private Boolean enabled = true;

	//@NotEmpty
	@NotNull
	@Column(nullable = false)
	private Integer intentosRestantes = 3;
	
	
	@Column(nullable = true, name = "primera_sesion", columnDefinition = "bit default 1")
	private Boolean primeraSesion = true;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
			name = "usuario_role", 
			schema="seguridad",
			uniqueConstraints = { @UniqueConstraint(columnNames = { "usuario_id","role_id" }) }, 
			joinColumns = @JoinColumn(name = "usuario_id"), 
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<Role> roles;

    @ManyToOne
    @JoinColumn(name="institucion_id", nullable = true)
    private Institucion institucion;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "no_notificar",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "registro_id")
    )
    private List<Registro> noNotificarRegistros;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Entrega> entregas;
    
    
    
    

	public Boolean getPrimeraSesion() {
		return primeraSesion;
	}

	public void setPrimeraSesion(Boolean primeraSesion) {
		this.primeraSesion = primeraSesion;
	}

	public Boolean setRol(Role rol) {
    	return roles.add(rol);
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getIntentosRestantes() {
		return intentosRestantes;
	}

	public void setIntentosRestantes(Integer intentosRestantes) {
		this.intentosRestantes = intentosRestantes;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}	
    
    
    
    

}