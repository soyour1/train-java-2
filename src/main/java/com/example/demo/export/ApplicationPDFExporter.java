package com.example.demo.export;

import com.example.demo.service.dto.response.ApplicationResponseDto;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@RequiredArgsConstructor
public class ApplicationPDFExporter {
	private List<ApplicationResponseDto> applicationList;
	
	public void writeTableHeader(PdfPTable table) {
		
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		cell.setPhrase(new Phrase("Image", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Application Id", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("E-mail", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Github User", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Projects", font));
		table.addCell(cell);
	}
	
	public void writeTableData(PdfPTable table) throws IOException {
		String imageUrl = "https://avatars.githubusercontent.com/";
		
		for (ApplicationResponseDto applicationList : applicationList) {
			Image image = Image.getInstance(imageUrl+ applicationList.getGitUser());
			table.addCell(image);
			table.addCell(String.valueOf(applicationList.getId()));
			table.addCell(applicationList.getName());
			table.addCell(applicationList.getEmail());
			table.addCell(applicationList.getGitUser());
			table.addCell(String.valueOf(applicationList.getProjects().size()));
			
			List<String> project = applicationList.getProjects().stream().map(entity -> {
				return String.format("Name:  %s \nEmployment: %s \nCapacity: %s \nDuration: %s \nStartYear: %s \nRole: %s \nTeamSize: %s \nLinkToRepository: %s \nLinkToLiveUrl: %s \n",
						  entity.getName(),entity.getEmploymentMode(), entity.getCapacityStatus(), entity.getCapacityStatus(),
						  entity.getStartYear(), entity.getRole(), entity.getTeamSize(), entity.getLinkRepo(), entity.getLinkLive());
			}).collect(Collectors.toList());
			String projects = String.join(" \n", project);
			table.addCell(projects);
		}
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A3);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(20);
		font.setColor(Color.BLUE);
		
		Paragraph p = new Paragraph("List of application", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(p);
		
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 2.5f, 2.0f, 2.0f, 1f });
		table.setSpacingBefore(10);
		
		writeTableHeader(table);
		writeTableData(table);
		
		document.add(table);
		
		document.close();
		
	}
}
