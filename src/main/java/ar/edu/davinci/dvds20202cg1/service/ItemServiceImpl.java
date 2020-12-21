package ar.edu.davinci.dvds20202cg1.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import ar.edu.davinci.dvds20202cg1.model.Item;
import ar.edu.davinci.dvds20202cg1.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemRepository itemRepository;
    
    @Autowired
    public ItemServiceImpl(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> listAll() {
        return itemRepository.findAll();
    }

    @Override
    public Page<Item> list(Pageable pageable) {
        LOGGER.info("Pagegable: offset: " + pageable.getOffset() + " - pageSize:" + pageable.getPageSize());
        return itemRepository.findAll(pageable);
    }

    @Override
    public Optional<Item> findById(Long id) {
    return itemRepository.findById(id);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
    
    @Override
    public long count() {
        return itemRepository.count();
    }
}