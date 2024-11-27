package org.rostislav.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.rostislav.controllers.NavigationController;
import org.rostislav.models.Payment;
import org.rostislav.models.RentalWithNames;
import org.rostislav.service.PaymentService;
import org.rostislav.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentsPanel extends JPanel implements NavigablePanel {

    private final PaymentService paymentService;
    private final RentalService rentalService;
    private final DefaultTableModel tableModel;

    @Autowired
    public PaymentsPanel(NavigationController navigationController, PaymentService paymentService, RentalService rentalService) {
        this.paymentService = paymentService;
        this.rentalService = rentalService;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Payments", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "User", "Car", "Amount", "Date", "Method", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add Payment");
        JButton editButton = new JButton("Edit Payment");
        JButton deleteButton = new JButton("Delete Payment");
        JButton backButton = new JButton("Back");

        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(backButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        loadPayments();

        addButton.addActionListener(e -> addPayment());
        editButton.addActionListener(e -> editPayment(table));
        deleteButton.addActionListener(e -> deletePayment(table));
        backButton.addActionListener(e -> navigationController.navigateTo("Dashboard"));
    }

    private void loadPayments() {
        tableModel.setRowCount(0);
        Collection<Payment> payments = paymentService.getAllPayments();
        for (Payment payment : payments) {
            RentalWithNames rental = rentalService.getRentalWithNames(payment.getRentalId());
            if (rental != null) {
                tableModel.addRow(new Object[] {
                        payment.getId(),
                        rental.getUserName(),
                        rental.getCarLicensePlate(),
                        payment.getAmount(),
                        payment.getDate(),
                        payment.getMethod(),
                        payment.getStatus()
                });
            }
        }
    }

    private void addPayment() {
        JComboBox<RentalWithNames> rentalComboBox = new JComboBox<>(getAllRentalsWithNames());
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField();
        JComboBox<String> methodComboBox = new JComboBox<>(new String[] {"Credit Card", "Debit Card", "Cash", "Bank Transfer"});
        JComboBox<String> statusComboBox = new JComboBox<>(new String[] {"Pending", "Completed", "Refunded"});

        Object[] fields = {
                "Rental:", rentalComboBox,
                "Amount:", amountField,
                "Date (YYYY-MM-DD):", dateField,
                "Payment Method:", methodComboBox,
                "Status:", statusComboBox
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Payment", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                RentalWithNames selectedRental = (RentalWithNames) rentalComboBox.getSelectedItem();
                BigDecimal amount = new BigDecimal(amountField.getText());
                String dateString = dateField.getText();
                String method = (String) methodComboBox.getSelectedItem();
                String status = (String) statusComboBox.getSelectedItem();

                if (selectedRental != null) {
                    paymentService.addPayment(selectedRental.getId(), amount, LocalDate.parse(dateString), method, status);
                    loadPayments();
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a rental.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (
                    Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editPayment(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int paymentId = (int) table.getValueAt(selectedRow, 0);
            Payment payment = paymentService.getPaymentById(paymentId);
            if (payment != null) {
                JComboBox<RentalWithNames> rentalComboBox = new JComboBox<>(getAllRentalsWithNames());
                rentalComboBox.setSelectedItem(rentalService.getRentalWithNames(payment.getRentalId()));
                JTextField amountField = new JTextField(payment.getAmount().toString());
                JTextField dateField = new JTextField(payment.getDate().toString());
                JComboBox<String> methodComboBox = new JComboBox<>(new String[] {"Credit Card", "Debit Card", "Cash", "Bank Transfer"});
                methodComboBox.setSelectedItem(payment.getMethod());
                JComboBox<String> statusComboBox = new JComboBox<>(new String[] {"Pending", "Completed", "Refunded"});
                statusComboBox.setSelectedItem(payment.getStatus());

                Object[] fields = {
                        "Rental:", rentalComboBox,
                        "Amount:", amountField,
                        "Date (YYYY-MM-DD):", dateField,
                        "Payment Method:", methodComboBox,
                        "Status:", statusComboBox
                };

                int option = JOptionPane.showConfirmDialog(this, fields, "Edit Payment", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        payment.setRentalId(((RentalWithNames) rentalComboBox.getSelectedItem()).getId());
                        payment.setAmount(new BigDecimal(amountField.getText()));
                        payment.setDate(LocalDate.parse(dateField.getText()));
                        payment.setMethod((String) methodComboBox.getSelectedItem());
                        payment.setStatus((String) statusComboBox.getSelectedItem());

                        paymentService.updatePayment(payment);
                        loadPayments();
                    } catch (
                            Exception e) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a payment to edit.", "Edit Payment", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deletePayment(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int paymentId = (int) table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this payment?", "Delete Payment", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                paymentService.removePayment(paymentId);
                loadPayments();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a payment to delete.", "Delete Payment", JOptionPane.WARNING_MESSAGE);
        }
    }

    private RentalWithNames[] getAllRentalsWithNames() {
        Collection<RentalWithNames> rentals = rentalService.getAllRentalsWithNames();
        return rentals.toArray(new RentalWithNames[0]);
    }

    @Override
    public String getPanelName() {
        return "PaymentsPanel";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
