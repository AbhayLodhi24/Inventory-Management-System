package com.project.ims.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.ims.Model.Supplier;
import com.project.ims.Model.SupplierDto;
import com.project.ims.Repository.SupplierRepository;
import com.project.ims.Service.SupplierService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/suppliers")
public class SupplierController {

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    SupplierService supplierService;


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
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("supp", supplierDTO); // Re-populate the form with submitted values
            return "Frontend/Admin/Supplier :: addSupplierModalContent"; // Return the modal content fragment
        }

        Supplier supplier = new Supplier();
        supplier.setSupplierName(supplierDTO.getSupplierName());
        supplier.setSupplierEmail(supplierDTO.getSupplierEmail());
        supplier.setSupplierPhno(supplierDTO.getSupplierPhno());
        supplier.setSupplierAddr(supplierDTO.getSupplierAddr());

        try {
            supplierService.saveSupplier(supplier);
            return "redirect:/admin/suppliers"; // Redirect on success
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("supp", supplierDTO); // Re-populate the form
            if (e.getMessage().contains("supplier_email_unique")) {
                model.addAttribute("emailError", "Supplier with this email already exists!");
            } else {
                model.addAttribute("generalError", "An error occurred while saving the supplier.");
            }
            return "Frontend/Admin/Supplier :: addSupplierModalContent"; // Return with error message
        }
    }


    @PostMapping("/edit/{id}")
    public String updateSupplier(@PathVariable int id,
                                 @Valid @ModelAttribute("supp") SupplierDto supplierDTO,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            supplierDTO.setSupplierId(id); // Ensure ID is retained for re-rendering
            model.addAttribute("supp", supplierDTO);
            return "Frontend/Admin/Supplier :: editSupplierModalContent"; // Return the edit modal content fragment
        }

        Optional<Supplier> existingSupplierOptional = supplierService.getSupplierById(id);
        if (!existingSupplierOptional.isPresent()) {
            return "redirect:/admin/suppliers?error=Supplier not found";
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
            supplierDTO.setSupplierId(id); // Ensure ID is retained
            model.addAttribute("supp", supplierDTO);
            if (e.getMessage().contains("supplier_email_unique")) {
                model.addAttribute("editEmailError", "Supplier with this email already exists!");
            } else {
                model.addAttribute("editGeneralError", "An error occurred while updating the supplier.");
            }
            return "Frontend/Admin/Supplier :: editSupplierModalContent"; // Return edit modal fragment with errors
        }
    }


}