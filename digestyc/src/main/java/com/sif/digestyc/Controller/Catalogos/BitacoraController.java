package com.sif.digestyc.Controller.Catalogos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sif.digestyc.Service.Security.SecurityImpl.BitacoraServiceImpl;

@Controller
@RequestMapping("/bitacora")
public class BitacoraController {
	
	private static final String BITACORAS = "security/bitacora";
	
	@Autowired
	@Qualifier("bitacoraServiceImpl")
	private BitacoraServiceImpl bitacoraService;

	@PreAuthorize("hasUrl('/bitacora/lista')")
	@GetMapping("/lista/{skip}/{inf}/")
	public ModelAndView getBitacoras(@PathVariable("skip") int skip, @PathVariable("inf") int inf, @Param(value="buscar") String buscar) {
		ModelAndView mav = new ModelAndView(BITACORAS);
		int cantidad = (int)bitacoraService.getCantidad(buscar)/skip;
		List<Integer> paginas = new ArrayList<Integer>();
		int desde = 0;
		if(cantidad>3) {
			if(inf>30) {
				desde = (int)inf/10-3;
				cantidad = (cantidad - (int)inf/10)>4?(int)inf/10+4:cantidad;
			}else {
				cantidad = 4;
			}
		}
		
		for(int i = desde; i<=cantidad;i++) {
			paginas.add(i);
		}
		if(skip >=10) {
			mav.addObject("skip", skip);
		}else {
			mav.addObject("skip", 10);
		}
		mav.addObject("paginas", paginas);
		mav.addObject("current", inf);
		mav.addObject("last", cantidad);
		if(buscar != null && buscar.isEmpty()) {
			mav.addObject("buscar", buscar);
		}else {
			mav.addObject("buscar", "");
		}
		if(buscar !=null && !buscar.isEmpty()) {
			mav.addObject("bitacoras", bitacoraService.buscarBitacoras(inf, inf + skip, buscar));
		}else {
			mav.addObject("bitacoras", bitacoraService.buscarBitacoras(inf, inf + skip));
		}
		return mav;
	}

	@PreAuthorize("hasUrl('/bitacora/lista')")
	@GetMapping("/lista")
	public String getBitacora() {
		return "redirect:/bitacora/lista/10/0/";
	}
	
	
	
}
