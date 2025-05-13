package com.project.ims.controller;
import com.project.ims.Dto.SupplierDto;
import com.project.ims.Model.Supplier;
import com.project.ims.Repository.CategoryRepository;
import com.project.ims.Repository.SupplierRepository;
import com.project.ims.Service.CategoryService;
import com.project.ims.Service.SupplierService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/admin/suppliers")
public class SupplierController {
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;
    
    @GetMapping
    public String getSuppliersPage(@RequestParam(value = "query", required = false, defaultValue = "") String query,
                                 Model model) {

        List<Supplier> suppliers = supplierService.getAllSuppliers(query);
        model.addAttribute("suppliersList", suppliers);
        model.addAttribute("query", query);
        SupplierDto supplierDTO = new SupplierDto();
        model.addAttribute("supp", supplierDTO);
        model.addAttribute("suppliers", supplierService.getAllSuppliersFromTable());
        return "/Frontend/Admin/Suppliers/suppliers";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable int id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = supplierService.deleteSupplier(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "Supplier deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Supplier not found!");
            return "redirect:/admin/suppliers"; // Redirects to the supplier list page
        }
        return "redirect:/admin/suppliers"; // Redirects to the supplier list page
    }
    @PostMapping("/addSupplier")
    public String addSuppliers(@Valid @ModelAttribute("supp") SupplierDto supplierDTO,
                               BindingResult bindingResult,
                               Model model,
                               HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("supp", supplierDTO);
            return "Frontend/Admin/Suppliers/suppliers :: addSupplierModalContent";
        }
        if (supplierService.supplierEmailExists(supplierDTO.getSupplierEmail())) {
            model.addAttribute("supp", supplierDTO);
            model.addAttribute("emailError", "Supplier with this email already exists!");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Frontend/Admin/Suppliers/suppliers :: addSupplierModalContent";
        }
        Supplier supplier = new Supplier();
        supplier.setSupplierName(supplierDTO.getSupplierName());
        supplier.setSupplierEmail(supplierDTO.getSupplierEmail());
        supplier.setSupplierPhno(supplierDTO.getSupplierPhno());
        supplier.setSupplierAddr(supplierDTO.getSupplierAddr());
        try {
            supplierService.saveSupplier(supplier);
            return "redirect:/admin/suppliers";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("supp", supplierDTO);
            if (e.getMessage().contains("supplier_email_unique")) {
                model.addAttribute("emailError", "Supplier with this email already exists!");
            } 
            if (e.getMessage().contains("unique constraint") && e.getMessage().contains("UNIQUE_SUPPLIER_NAME")) {
                model.addAttribute("nameError", "Supplier with this name already exists!");
            }
            if (e.getMessage().contains("unique constraint") && e.getMessage().contains("UNIQUE_SUPPLIER_PHONE_NUMBER")) {
                model.addAttribute("phoneError", "Supplier with this phone number already exists!");
            } 
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // SET ERROR STATUS
            return "Frontend/Admin/Suppliers/suppliers :: addSupplierModalContent";
        }
    }
    @PostMapping("/editSupplier")
    public String editSupplier(@RequestParam(value = "id", required = false) Integer id,
                               @Valid @ModelAttribute("supp") SupplierDto supplierDTO,
                               BindingResult bindingResult,
                               Model model,
                               HttpServletResponse response) {
        if (id != null) {
            Optional<Supplier> supplierOptional = supplierService.getSupplierById(id);
            if (supplierOptional.isPresent()) {
                Supplier supplier = supplierOptional.get();
                supplierDTO.setSupplierId(supplier.getSupplierId());
                supplierDTO.setSupplierName(supplier.getSupplierName());
                supplierDTO.setSupplierEmail(supplier.getSupplierEmail());
                supplierDTO.setSupplierPhno(supplier.getSupplierPhno());
                supplierDTO.setSupplierAddr(supplier.getSupplierAddr());
                model.addAttribute("supp", supplierDTO);
                return "Frontend/Admin/Suppliers/suppliers :: editSupplierModalContent"; // Consistent path
            } else {
                return "redirect:/admin/suppliers?error=Supplier not found";
            }
        }
        // If no ID is provided in the POST, assume it's an update submission
        if (bindingResult.hasErrors()) {
            model.addAttribute("supp", supplierDTO);
            return "Frontend/Admin/Suppliers/suppliers :: editSupplierModalContent"; // Consistent path
        }
        if (supplierDTO.getSupplierId() == null) {
            return "redirect:/admin/suppliers?error=Invalid supplier ID for update";
        }
        Optional<Supplier> existingSupplierOptional = supplierService.getSupplierById(supplierDTO.getSupplierId());
        if (!existingSupplierOptional.isPresent()) {
            model.addAttribute("editGeneralError", "Supplier not found.");
            return "Frontend/Admin/Suppliers/suppliers :: editSupplierModalContent"; // Consistent path
        }
        Supplier existingSupplier = existingSupplierOptional.get();
        existingSupplier.setSupplierName(supplierDTO.getSupplierName());
        existingSupplier.setSupplierEmail(supplierDTO.getSupplierEmail());
        existingSupplier.setSupplierPhno(supplierDTO.getSupplierPhno());
        existingSupplier.setSupplierAddr(supplierDTO.getSupplierAddr());
        try {
            supplierService.updateSupplier(existingSupplier);
            return "redirect:/admin/suppliers?message=Supplier updated successfully";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("supp", supplierDTO);
            if (e.getMessage().contains("unique constraint") && e.getMessage().contains("UKA86FHNVGPNTBDMH6RG2543OO8")) {
                model.addAttribute("editEmailError", "Supplier with this email already exists!");
            }
            if (e.getMessage().contains("unique constraint") && e.getMessage().contains("UNIQUE_SUPPLIER_NAME")) {
                model.addAttribute("editNameError", "Supplier with this name already exists!");
            }
            if (e.getMessage().contains("unique constraint") && e.getMessage().contains("UNIQUE_SUPPLIER_PHONE_NUMBER")) {
                model.addAttribute("editPhoneError", "Supplier with this phone number already exists!");
            } 
//            else {
//                model.addAttribute("editGeneralError", "An error occurred while updating the supplier.");
//            }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // SET ERROR STATUS
            return "Frontend/Admin/Suppliers/suppliers :: editSupplierModalContent"; // Consistent path
        } catch (Exception e) {
            model.addAttribute("supp", supplierDTO);
            model.addAttribute("editGeneralError", "An unexpected error occurred: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Or another appropriate error code
            return "Frontend/Admin/Suppliers/suppliers :: editSupplierModalContent"; // Consistent path
        }
    }
}
