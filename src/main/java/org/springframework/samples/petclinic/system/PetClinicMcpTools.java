/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.system;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.stereotype.Component;

/**
 * MCP Tools exposing PetClinic data to AI assistants via the Model Context Protocol. Each
 * method annotated with {@link Tool} becomes an invocable tool in Copilot.
 */
@Component
public class PetClinicMcpTools {

	private final OwnerRepository ownerRepository;

	private final VetRepository vetRepository;

	public PetClinicMcpTools(OwnerRepository ownerRepository, VetRepository vetRepository) {
		this.ownerRepository = ownerRepository;
		this.vetRepository = vetRepository;
	}

	@McpTool(
			description = "Find owners by last name (partial match, case-insensitive). Returns id, full name, address, city, phone and pets.")
	public List<String> findOwnersByLastName(
			@McpToolParam(description = "Last name prefix to search for, e.g. 'Davis'") String lastName) {
		return ownerRepository.findByLastNameStartingWith(lastName, PageRequest.of(0, 20))
			.stream()
			.map(this::formatOwner)
			.collect(Collectors.toList());
	}

	@McpTool(description = "List all vets with their specialties.")
	public List<String> listVets() {
		return vetRepository.findAll().stream().map(this::formatVet).collect(Collectors.toList());
	}

	@McpTool(description = "Count total number of owners registered in the clinic.")
	public long countOwners() {
		return ownerRepository.count();
	}

	@McpTool(description = "List all pets for a given owner id.")
	public String listPetsForOwner(@McpToolParam(description = "The numeric id of the owner") int ownerId) {
		return ownerRepository.findById(ownerId)
			.map(owner -> owner.getPets()
				.stream()
				.map(pet -> pet.getName() + " (" + pet.getType().getName() + ")")
				.collect(Collectors.joining(", ")))
			.orElse("Owner not found with id " + ownerId);
	}

	private String formatOwner(Owner owner) {
		String pets = owner.getPets().stream().map(p -> p.getName()).collect(Collectors.joining(", "));
		return "id=%d | %s %s | %s, %s | tel:%s | pets:[%s]".formatted(owner.getId(), owner.getFirstName(),
				owner.getLastName(), owner.getAddress(), owner.getCity(), owner.getTelephone(), pets);
	}

	private String formatVet(Vet vet) {
		String specialties = vet.getSpecialties().stream().map(s -> s.getName()).collect(Collectors.joining(", "));
		return "%s %s | specialties:[%s]".formatted(vet.getFirstName(), vet.getLastName(),
				specialties.isEmpty() ? "none" : specialties);
	}

}
