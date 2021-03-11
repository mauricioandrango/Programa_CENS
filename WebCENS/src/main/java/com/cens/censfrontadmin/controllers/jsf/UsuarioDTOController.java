package com.cens.censfrontadmin.controllers.jsf;

import com.cens.censfrontadmin.dto.UsuarioDTO;
import com.cens.censfrontadmin.controllers.jsf.util.JsfUtil;
import com.cens.censfrontadmin.controllers.jsf.util.JsfUtil.PersistAction;
import java.net.MalformedURLException;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;



import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import static java.util.Collections.reverseOrder;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

@ManagedBean 
@SessionScoped
public class UsuarioDTOController implements Serializable {

    private Client wsClient;
    private WebTarget wgetTarget;
    private static String URL_BASE="http://localhost:80/";
    private UsuarioDTO selected;

    public UsuarioDTOController() {
        selected = new UsuarioDTO();
        wsClient = ClientBuilder.newClient();
        wgetTarget = wsClient.target(URL_BASE).path("allUsuarios");
    }

    public UsuarioDTO getSelected() {
        return selected;
    }

    public void setSelected(UsuarioDTO selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }


    public UsuarioDTO create() {
        initializeEmbeddableKey();
        try {
            createWS(selected);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDTOController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return selected;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void createWS(UsuarioDTO entity) throws Exception{
        try{
            URL url = new URL("http://localhost:80/createUsuario"); 
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            conection.setDoOutput(true);
            conection.setRequestMethod("POST");
            conection.setRequestProperty("Content-Type", "application/json");
            ObjectMapper mapper = new ObjectMapper();
            String string = mapper.writeValueAsString(entity);
            OutputStream os = conection.getOutputStream();
            os.write(string.getBytes());
            os.flush();
            
            if(conection.getResponseCode()!= HttpURLConnection.HTTP_OK){
             throw new RuntimeException("Failed : HTTP error code : "
                    + conection.getResponseCode());
            }
             BufferedReader br = new BufferedReader(new InputStreamReader(
                (conection.getInputStream())));
        conection.disconnect();
        JsfUtil.addSuccessMessage("Se creo exitosamenete el usuario.");
        }catch (MalformedURLException e){
        }catch (IOException e){
        }  
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioDTOUpdated"));
    }
    
    public void delete(){
         JsfUtil.addSuccessMessage("Usuario Borrado");
        remove(selected);
    }
    
    
    public void remove(UsuarioDTO entity){
        String id = String.valueOf(entity.getId())+"";
        WebTarget removeTarget = wsClient.target(URL_BASE).path("deleteUsuario/"+id);
        String get = removeTarget.request().delete(String.class);
        System.out.println(get);
    }
    public List<UsuarioDTO> getItems() {
        return findAll();
    }

    private void persist(PersistAction persistAction, String successMessage) {
         if (getSelected() != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    edit(getSelected());
                } else {
                    remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
         
    }
    
    public List<UsuarioDTO> castToList(JSONArray users){
        List<UsuarioDTO> list = new ArrayList<>();
        //lista inversa 
        for(int i = users.length()-1 ;i>=0;i--){
            UsuarioDTO usr = new UsuarioDTO(users.get(i).toString());
            list.add(usr);
        }
        return list;
    }
    public List<UsuarioDTO> findAll(){
      WebTarget resource = wgetTarget;
      String get = resource.request(MediaType.APPLICATION_JSON).get(String.class);
      JSONObject object = new JSONObject(get);
      return castToList(object.getJSONArray("data"));
    }
    
    public UsuarioDTO getUsuarioDTO(java.lang.Long id) {
        //return find(id);r
        return null;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void edit(UsuarioDTO entity){
        try{
            URL url = new URL("http://localhost:80/updateUsuario"); 
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            conection.setDoOutput(true);
            conection.setRequestMethod("POST");
            conection.setRequestProperty("Content-Type", "application/json");
            ObjectMapper mapper = new ObjectMapper();
            String string = mapper.writeValueAsString(entity);
            OutputStream os = conection.getOutputStream();
            os.write(string.getBytes());
            os.flush();
            
            if(conection.getResponseCode()!= HttpURLConnection.HTTP_OK){
             throw new RuntimeException("Failed : HTTP error code : "
                    + conection.getResponseCode());
            }
             BufferedReader br = new BufferedReader(new InputStreamReader(
                (conection.getInputStream())));
        conection.disconnect();
            
        }catch (MalformedURLException e){
        }catch (IOException e){
        }  
    }

    public List<UsuarioDTO> getItemsAvailableSelectMany() {
        return findAll();
    }

    public List<UsuarioDTO> getItemsAvailableSelectOne() {
        return findAll();
    }

    @FacesConverter(forClass = UsuarioDTO.class)
    public static class UsuarioDTOControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioDTOController controller = (UsuarioDTOController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioDTOController");
            return controller.getUsuarioDTO(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof UsuarioDTO) {
                UsuarioDTO o = (UsuarioDTO) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UsuarioDTO.class.getName()});
                return null;
            }
        }

    }

}
