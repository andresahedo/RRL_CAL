/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.util.validador;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoClasificacionArancelaria;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Documento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FraccionDuda;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.base.util.validator.BaseValidator;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean que atendera las reglas de negocio del registro de la
 * solicitud.
 * 
 * @author Softtek
 * 
 */
@Component
public class RegistroSolicitudConsultaAutorizacionValidator extends BaseValidator {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -3500550713126391885L;

    /**
     * Metodo para validar un apersona solicitante
     * 
     * @param persona
     * @throws SolicitudNoGuardadaException
     */
    public void validarSolicitante(Persona persona) throws SolicitudNoGuardadaException {
        if (persona == null) {
            throw new SolicitudNoGuardadaException(RegistroSolicitudConstants.SOLICITUD_NO_GUARDADA_SOLICITANTE);
        }
    }

    /**
     * Metodo para validar unidad administrativa
     * 
     * @param claveunidad
     * @throws SolicitudNoGuardadaException
     */
    public void validarUnidadAdministrativaSolicitud(String claveunidad) throws SolicitudNoGuardadaException {
        if (claveunidad == null) {
            throw new SolicitudNoGuardadaException(RegistroSolicitudConstants.SOLICITUD_NO_GUARDADA_UA);
        }
    }

    /**
     * Metodo para validar documentos requeridos
     * 
     * @param documentos
     * @throws ArchivoNoGuardadoException
     */
    public void validarDocumentosRequeridos(List<Documento> documentos) throws ArchivoNoGuardadoException {
        if (documentos.isEmpty()) {
            throw new ArchivoNoGuardadoException(RegistroSolicitudConstants.DOCUMENTOS_REQUERIDOS_EMPTY);
        }
    }

    /**
     * Metodo para validar si un documetno es duplicado
     * 
     * @param documento
     * @param listaDocumentos
     * @throws ArchivoNoGuardadoException
     */
    public void validarNoDuplicidadDocumento(Documento documento, List<Documento> listaDocumentos)
            throws ArchivoNoGuardadoException {
        for (Documento doc : listaDocumentos) {
            if (doc.getNombre().equals(documento.getNombre())) {
                throw new ArchivoNoGuardadoException(RegistroSolicitudConstants.DOCUMENTO_DUPLICADO);
            }
        }
    }

    /**
     * Metodo para validar el tamaio minimo y maximo de un documento
     * 
     * @param documento
     * @param tamanioArchivo
     * @throws ArchivoNoGuardadoException
     */

    public void validarTamanioMaximo(Documento documento, long tamanioArchivo) throws ArchivoNoGuardadoException {
        String extension = obtenerExtension(documento.getNombre());
        long maxFileSizeBytes = 0L;
        String mensaje = null;
        if (extension.equals(RegistroSolicitudConstants.EXTENSION_ARCHIVO_PDF)) {
            // Maximo 6MB
            maxFileSizeBytes = (NumerosConstantes.MIL_VEINTICUATRO * NumerosConstantes.MIL_VEINTICUATRO) * NumerosConstantes.TRES;
            mensaje = RegistroSolicitudConstants.TAMANIO_MAXIMO_SUPERADO_PDF;
        }
        else if (extension.equals(RegistroSolicitudConstants.EXTENSION_ARCHIVO_JPG)) {
            // Maximo 3MB
            maxFileSizeBytes = (NumerosConstantes.MIL_VEINTICUATRO * NumerosConstantes.MIL_VEINTICUATRO) * NumerosConstantes.TRES;
            mensaje = RegistroSolicitudConstants.TAMANIO_MAXIMO_SUPERADO_JPG;
        }

        if (tamanioArchivo > maxFileSizeBytes) {
            throw new ArchivoNoGuardadoException(mensaje);
        }

    }

    /**
     * Metodo para validar un documento obligatorio
     * 
     * @param documentos
     * @param listaObligatorios
     * @throws ArchivoNoGuardadoException
     */
    public void validarDocumentosObligatorios(List<Documento> documentos, List<DocumentoTramite> listaObligatorios)
            throws ArchivoNoGuardadoException {
        List<DocumentoTramite> documentosNoAgregados = new ArrayList<DocumentoTramite>();
        boolean encontrado = false;

        for (DocumentoTramite documentoCombo : listaObligatorios) {
            encontrado = false;
            for (Documento documento : documentos) {
                if (documentoCombo.getTipoDoc().getIdTipoDocumento().equals(documento.getIdTipoDocumento())) {
                    encontrado = true;
                    continue;
                }
            }
            if (encontrado) {
                continue;
            }
            documentosNoAgregados.add(documentoCombo);
        }
        if (!documentosNoAgregados.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (DocumentoTramite doc : documentosNoAgregados) {
                sb.append(doc.getTipoDoc().getNombre());
                sb.append(". ");

            }
            throw new ArchivoNoGuardadoException(sb.toString());
        }
    }

    /**
     * Metodo para validar un documento por anexar
     * 
     * @param documentos
     * @param listaObligatorios
     * @throws ArchivoNoGuardadoException
     */
    public void validarDocumentosPorAnexar(List<Documento> documentos, List<DocumentoTramite> listaObligatorios)
            throws ArchivoNoGuardadoException {
        boolean encontrado = false;

        for (DocumentoTramite documentoCombo : listaObligatorios) {
            encontrado = false;
            for (Documento documento : documentos) {
                if (documentoCombo.getTipoDoc().getIdTipoDocumento().equals(documento.getIdTipoDocumento())) {
                    encontrado = true;
                    continue;
                }
            }
            if (encontrado) {
                continue;
            }
            throw new ArchivoNoGuardadoException("Falta adjuntar documentaci\u00F3n requerida.  Verifique");
        }
    }

    /**
     * Implementa validaciones: (1) no se puede tener una
     * fracci&oacute;n arancelaria como fracci&oacute;n aplicable y en
     * la que exista duda. (2) Debe haber registrado fracciones
     * arancelarias con las que existe duda si la opci&oacute;n
     * elegida fue la 1
     * 
     * @param fraccionAplicable
     * @param fraccionesDuda
     * @throws SolicitudNoGuardadaException
     */
    public void validarFraccionesArancelarias(String ideClasificacionArancelaria, String fraccionAplicable,
            List<FraccionDuda> fraccionesDuda) throws SolicitudNoGuardadaException {
        if (TipoClasificacionArancelaria.CLASIFICACION_CLAS.getClave().equals(ideClasificacionArancelaria)
                && fraccionesDuda != null && fraccionesDuda.isEmpty()) {
            throw new SolicitudNoGuardadaException(
                    "Para la Opci\u00F3n 1 es obligatorio registrar la fracci\u00F3n arancelaria que se considera aplicable as\u00ED como las fracciones arancelarias con las que existe duda.");
        }

        if (fraccionAplicable != null && fraccionesDuda != null && !fraccionesDuda.isEmpty()) {
            for (FraccionDuda fraccionDuda : fraccionesDuda) {
                if (fraccionDuda.getFraccionDuda().toString().equals(fraccionAplicable)) {
                    throw new SolicitudNoGuardadaException(
                            "El valor de las fracciones arancelarias en las que existe duda debe ser diferente que el valor de la fracci\u00F3n que se considera aplicable.");
                }
            }
        }

    }

    /**
     * Metodo para obtener la extencion de un documento
     * 
     * @param nombreArchivo
     * @return
     */
    private String obtenerExtension(String nombreArchivo) {
        String ext = null;
        int i = nombreArchivo.lastIndexOf('.');
        if (i > 0 && i < nombreArchivo.length() - 1) {
            ext = nombreArchivo.substring(i + 1).toUpperCase();
        }
        return ext;
    }

    /**
     * M&eacute;todo para validar los documentos marcados como
     * requeridos en la selecci&oacute;n vs los que va anexando el
     * usuario
     * 
     * @param documentosS
     * @param documentosR
     * @throws ArchivoNoGuardadoException
     */
    public void validarDocumentosPorAnexarRequeridos(List<Documento> documentosS, List<Documento> documentosR)
            throws ArchivoNoGuardadoException {
        boolean encontrado = false;

        for (Documento documentoCombo : documentosR) {
            encontrado = false;
            for (Documento documento : documentosS) {
                if (documentoCombo.getIdTipoDocumento().equals(documento.getIdTipoDocumento())) {
                    encontrado = true;
                    continue;
                }
            }
            if (encontrado) {
                continue;
            }
            throw new ArchivoNoGuardadoException(
                    "Debe adjuntar todos los documentos que seleccion\u00F3 del asunto los cuales son:");
        }
    }

}
