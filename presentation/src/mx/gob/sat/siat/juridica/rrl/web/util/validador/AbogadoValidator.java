package mx.gob.sat.siat.juridica.rrl.web.util.validador;

import mx.gob.sat.siat.juridica.base.web.util.validador.BaseValidator;
import mx.gob.sat.siat.juridica.rrl.dto.ConceptosDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.AbogadoConstantes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "abogadoValidator")
@NoneScoped
public class AbogadoValidator extends BaseValidator {

    /**
     * 
     */
    private static final long serialVersionUID = -4510524717114567300L;

    public Map<Integer, String> resolucionNueva(
            ResolucionImpugnadaDTO resolDto, String resolImpugnada,
            List<ResolucionImpugnadaDTO> resolucionesDTOList) {
        Map<Integer, String> msgs = new HashMap<Integer, String>();
        if (resolucionesDTOList != null && !resolucionesDTOList.isEmpty()) {
            for (ResolucionImpugnadaDTO resolucionImpugnadaDTO : resolucionesDTOList) {
                if (resolucionImpugnadaDTO.getResImpugnada() != null
                        && !resolucionImpugnadaDTO.getResImpugnada().isEmpty()) {
                    if (resolucionImpugnadaDTO.getResImpugnada().equals(
                            resolImpugnada)) {
                        msgs.put(
                                AbogadoConstantes.RESOLUCION_REPETIDA,
                                "La resoluci\u00F3n "
                                        + resolImpugnada
                                        + " ya se encuentra registrada para el asunto que se est\u00E1 atendiendo.");
                    }
                }
            }
        }
        if (resolDto.getIdTramite() != null
                && resolDto.getIdTramite().length() != 0
                && resolDto.getAutoridadEmisora() != null) {
            msgs.put(
                    AbogadoConstantes.RESOLUCION_DUPLICADA,
                    "La resoluci\u00F3n impugnada capturada "
                            + resolImpugnada
                            + " es la misma que se encuentra en el Recurso de Revocaci\u00F3n "
                            + resolDto.getIdTramite()
                            + " controlada por la unidad administrativa "
                            + resolDto.getAutoridadEmisora().getNombre());
        }
        return msgs;
    }

    public Map<Integer, String> resolucionModificada(
            ResolucionImpugnadaDTO resolDto, String resolImpugnada,
            List<ResolucionImpugnadaDTO> resolucionesDTOList) {

        Map<Integer, String> msgs = new HashMap<Integer, String>();

        if (resolucionesDTOList != null && !resolucionesDTOList.isEmpty()) {
            for (ResolucionImpugnadaDTO resolucionImpugnadaDTO : resolucionesDTOList) {
                if (resolucionImpugnadaDTO.getResImpugnada() != null
                        && !resolucionImpugnadaDTO.getResImpugnada().isEmpty()) {
                    if (!resolucionImpugnadaDTO.getResImpugnada().equals(
                            resolImpugnada)) {

                        if (resolDto.getIdTramite() != null
                                && resolDto.getIdTramite().length() != 0
                                && resolDto.getAutoridadEmisora() != null) {
                            msgs.put(
                                    AbogadoConstantes.RESOLUCION_DUPLICADA,
                                    "La resoluci\u00F3n impugnada capturada "
                                            + resolImpugnada
                                            + " es la misma que se encuentra en el Recurso de Revocaci\u00F3n "
                                            + resolDto.getIdTramite()
                                            + " controlada por la unidad administrativa "
                                            + resolDto.getAutoridadEmisora()
                                                    .getNombre());
                        }
                    }
                }

            }
        }

        return msgs;
    }

    public Map<Integer, String> conceptoNuevo(ConceptosDTO conceptosDTOactual,
            List<ConceptosDTO> listConceptosDTOTable) {
        Map<Integer, String> msgs = new HashMap<Integer, String>();
        if (listConceptosDTOTable != null && !listConceptosDTOTable.isEmpty()) {
            for (ConceptosDTO conceptoDTO : listConceptosDTOTable) {
                if (conceptoDTO.getConceptoValor() != null) {
                    if (conceptosDTOactual.getId() == null
                            || conceptoDTO.getId().longValue() != conceptosDTOactual
                                    .getId().longValue()) {
                        if (conceptoDTO
                                .getConceptoValor()
                                .getClave()
                                .equals(conceptosDTOactual.getConceptoValor()
                                        .getClave())) {

                            if (conceptoDTO.getImporte().compareTo(
                                    conceptosDTOactual.getImporte()) == 0) {
                                msgs.put(AbogadoConstantes.CONCEPTO_DUPLICADO,
                                        "El monto indicado ya fue capturado, favor de verificar.");
                            }
                        }
                    }
                }
            }
        }
        return msgs;

    }

}
