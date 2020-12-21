package ar.edu.davinci.dvds20202cg1.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.davinci.dvds20202cg1.model.Cliente;
import ar.edu.davinci.dvds20202cg1.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteRepository clienteRepository;
    
    @Autowired
    public ClienteServiceImpl(final ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> listAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Page<Cliente> list(Pageable pageable) {
        LOGGER.info("Pagegable: offset: " + pageable.getOffset() + " - pageSize:" + pageable.getPageSize());
        return clienteRepository.findAll(pageable);
    }

    @Override
    public Optional<Cliente> findById(Long id) {
    return clienteRepository.findById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void delete(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }
    
    @Override
    public long count() {
        return clienteRepository.count();
    }
}