package com.cens.censfrontadmin.controllers;

import com.cens.censfrontadmin.dto.PreguntaDTO;
import com.cens.censfrontadmin.controllers.util.JsfUtil;
import com.cens.censfrontadmin.controllers.util.PaginationHelper;
import com.cens.censfrontadmin.dto.EncuestaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import static java.util.concurrent.ThreadLocalRandom.current;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Init;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
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
public class PreguntaDTOController implements Serializable {

    private PreguntaDTO selected;
    private DataModel items = null;
    private Client wsClient;
    private WebTarget wgetTarget;
    private static String URL_BASE="http://localhost:80/";
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Map<String,String> tipoDePreguntas;
    private Map<String,Long> allEncuestas;
    private String encuestaSelected;

    
    @PostConstruct
    public void init(){
        allEncuestas = new HashMap<>();
        tipoDePreguntas = new HashMap<>();
        WebTarget findAllTarget = wsClient.target(URL_BASE).path("allEncuestas");
        String get = findAllTarget.request(MediaType.APPLICATION_JSON).get(String.class);
        JSONObject object = new JSONObject(get);
        List<EncuestaDTO> encuestas =  castToListEncuesta(object.getJSONArray("data"));
        for(EncuestaDTO encuesta:encuestas){
            allEncuestas.put(encuesta.getNombre(), encuesta.getId());
        }
        tipoDePreguntas.put("NUMERICA", "NUMERICA");
        tipoDePreguntas.put("ABIERTA", "ABIERTA");
        tipoDePreguntas.put("OPCIONES", "OPCIONES");
        
    }
    
    public List<EncuestaDTO> castToListEncuesta(JSONArray encuestas){
        List<EncuestaDTO> list = new ArrayList<>();
        for(int i=0;i<encuestas.length();i++){
            EncuestaDTO encuesta = new EncuestaDTO(encuestas.get(i).toString());
            list.add(encuesta);
        }
        return list;
    }
    
    public PreguntaDTOController() {
          wsClient = ClientBuilder.newClient();
    }

    public PreguntaDTO getSelected() {
        if (selected == null) {
            selected = new PreguntaDTO();
            selectedItemIndex = -1;
        }else{
        if(selected !=null){
        Integer ecid = selected.getEncuesta_id();
        if(ecid!=null){
         Long encuestaId = new Long(ecid);
         if(allEncuestas.containsValue(encuestaId)){
             for(String key:allEncuestas.keySet()){
                boolean isEncuesta = allEncuestas.get(key).equals(encuestaId);
                if(isEncuesta){
                    this.encuestaSelected=key;
                }
             }
         }
        }
        }
        }
        return selected;
    }

    public void setSelected(PreguntaDTO selected) {
        this.selected = selected;
    }
    
    

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return findAll().size();
                    //return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(findAll());
                    //return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        selected = (PreguntaDTO) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        selected = new PreguntaDTO();
        selectedItemIndex = -1;
        return "Create";
    }
    public String create() {
        try {
            createWS(selected);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundletwo").getString("PreguntaDTOCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundletwo").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        selected = (PreguntaDTO) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            edit(selected);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundletwo").getString("PreguntaDTOUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundletwo").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        selected = (PreguntaDTO) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateselectedItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    public String getEncuestaSelected() {
        return encuestaSelected;
    }

    public void setEncuestaSelected(String encuestaSelected) {
        this.encuestaSelected = encuestaSelected;
    }

    

    public Map<String, Long> getAllEncuestas() {
        return allEncuestas;
    }

    public void setAllEncuestas(Map<String, Long> allEncuestas) {
        this.allEncuestas = allEncuestas;
    }

    private void performDestroy() {
        try {
            remove(selected);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundletwo").getString("PreguntaDTODeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundletwo").getString("PersistenceErrorOccured"));
        }
    }

    private void updateselectedItem() {
        int count = count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            //selected = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(findAll(), true);
    }

    public PreguntaDTO getPreguntaDTO(java.lang.Long id) {
        return find(id);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    private void createWS(PreguntaDTO selected) {
       
        try{
           if(encuestaSelected!=null){
               selected.setEncuesta_id(Integer.parseInt(encuestaSelected));
           }
           else{
                throw new Error("No encuesta seleccionada");
           }
            URL url = new URL("http://localhost:80/createPregunta"); 
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            conection.setDoOutput(true);
            conection.setRequestMethod("POST");
            conection.setRequestProperty("Content-Type", "application/json");
            ObjectMapper mapper = new ObjectMapper();
            String string = mapper.writeValueAsString(selected);
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

    private void edit(PreguntaDTO selected) {
        try{
            URL url = new URL("http://localhost:80/updatePregunta"); 
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            selected.setEncuesta_id(Integer.parseInt(this.encuestaSelected));
            conection.setDoOutput(true);
            conection.setRequestMethod("POST");
            conection.setRequestProperty("Content-Type", "application/json");
            ObjectMapper mapper = new ObjectMapper();
            String string = mapper.writeValueAsString(selected);
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

    private void remove(PreguntaDTO selected) {
        String id = String.valueOf(selected.getId())+"";
        WebTarget removeTarget = wsClient.target(URL_BASE).path("deletePregunta/"+id);
        String get = removeTarget.request().delete(String.class);
        System.out.println(get);
    }

    private int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<?> findAll() {
        WebTarget findAllTarget = wsClient.target(URL_BASE).path("allPreguntas");
        String get = findAllTarget.request(MediaType.APPLICATION_JSON).get(String.class);
        JSONObject object = new JSONObject(get);
        return castToList(object.getJSONArray("data"));
    }
    
      public List<PreguntaDTO> castToList(JSONArray encuestas){
        List<PreguntaDTO> list = new ArrayList<>();
        for(int i=encuestas.length()-1;i>=0;i--){
            PreguntaDTO pregunta = new PreguntaDTO(encuestas.get(i).toString());
            list.add(pregunta);
        }
        return list;
    }

    private PreguntaDTO find(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FacesConverter(forClass = PreguntaDTO.class)
    public static class PreguntaDTOControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PreguntaDTOController controller = (PreguntaDTOController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "preguntaDTOController");
            return controller.getPreguntaDTO(getKey(value));
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
            if (object instanceof PreguntaDTO) {
                PreguntaDTO o = (PreguntaDTO) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PreguntaDTO.class.getName());
            }
        }

    }

}
