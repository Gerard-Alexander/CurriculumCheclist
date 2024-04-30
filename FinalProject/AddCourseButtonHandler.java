/**
 * @author Ravone Ebeng
 *
 */

private class AddCourseButtonHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            String courseNumber = JOptionPane.showInputDialog(mainFrame, "Enter the course number:");
            if (courseNumber != null && !courseNumber.isEmpty()) {
                String descriptiveTitle = JOptionPane.showInputDialog(mainFrame, "Enter the descriptive title:");
                if (descriptiveTitle != null && !descriptiveTitle.isEmpty()) {
                    String unitsStr = JOptionPane.showInputDialog(mainFrame, "Enter the units (must be a number):");
                    if (unitsStr != null && !unitsStr.isEmpty()) {
                        try {
                            double units = Double.parseDouble(unitsStr);
                            // Create a new Course object
                            Course course = new Course((byte)1, (byte)1, courseNumber, descriptiveTitle, units, 0, "", "", false, false);
                            // Add the course to the list of courses
                            // courses.add(course);
                            // Notify user of successful addition
                            JOptionPane.showMessageDialog(mainFrame, "Course added successfully.", "Add Course", JOptionPane.INFORMATION_MESSAGE);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(mainFrame, "Units must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }
}
