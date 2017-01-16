

package abhrainccore;

import java.util.*;
import java.io.Serializable;
import de.hybris.platform.util.*;
import de.hybris.platform.core.*;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.type.*;
import de.hybris.platform.persistence.type.*;
import de.hybris.platform.persistence.enumeration.*;
import de.hybris.platform.persistence.property.PersistenceManager;
import de.hybris.platform.persistence.*;

/**
 * Generated by hybris Platform.
 */
@SuppressWarnings({"cast","unused","boxing","null", "PMD"})
public class GeneratedTypeInitializer extends AbstractTypeInitializer
{
	/**
	 * Generated by hybris Platform.
	 */
	public GeneratedTypeInitializer( ManagerEJB manager, Map params )
	{
		super( manager, params );
	}


	/**
	 * Generated by hybris Platform.
	 */
	@Override
	protected void performRemoveObjects( ManagerEJB manager, Map params ) throws JaloBusinessException
	{
		// no-op by now
	}

	/**
	 * Generated by hybris Platform.
	 */
	@Override
	protected final void performCreateTypes( final ManagerEJB manager, Map params ) throws JaloBusinessException
	{
		// performCreateTypes
	
	
		createItemType(
<<<<<<< HEAD
=======
			"AbhraincOrder",
			"Order",
			com.abhrainc.core.jalo.AbhraincOrder.class,
			null,
			false,
			null,
			false
		);
	
		createItemType(
<<<<<<< HEAD
>>>>>>> sujan
=======
			"GoogleProductSearch",
			"CronJob",
			com.abhrainc.core.jalo.GoogleProductSearch.class,
			null,
			false,
			null,
			false
		);
	
		createItemType(
>>>>>>> naresh
			"ApparelProduct",
			"Product",
			com.abhrainc.core.jalo.ApparelProduct.class,
			null,
			false,
			null,
			false
		);
	
		createItemType(
			"ApparelStyleVariantProduct",
			"VariantProduct",
			com.abhrainc.core.jalo.ApparelStyleVariantProduct.class,
			null,
			false,
			null,
			false
		);
	
		createItemType(
			"ApparelSizeVariantProduct",
			"ApparelStyleVariantProduct",
			com.abhrainc.core.jalo.ApparelSizeVariantProduct.class,
			null,
			false,
			null,
			false
		);
	
		createItemType(
			"ElectronicsColorVariantProduct",
			"VariantProduct",
			com.abhrainc.core.jalo.ElectronicsColorVariantProduct.class,
			null,
			false,
			null,
			false
		);
	
		createItemType(
			"OrderThresholdPercentageDiscount",
			"OrderPromotion",
			com.abhrainc.core.jalo.OrderThresholdPercentageDiscount.class,
			null,
			false,
			null,
			false
		);
	
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> praneeth
		createEnumerationType(
			"SwatchColorEnum",
			null
		);
	
>>>>>>> sujan
		createCollectionType(
			"GenderList",
			"Gender",
			CollectionType.LIST
		);
	
		createCollectionType(
			"SwatchColorSet",
			"SwatchColorEnum",
			CollectionType.SET
		);
	
	}

	/**
	 * Generated by hybris Platform.
	 */
	@Override
	protected final void performModifyTypes( final ManagerEJB manager, Map params ) throws JaloBusinessException
	{
		// performModifyTypes
	

	
	
<<<<<<< HEAD
				single_createattr_Order_expectedDeliveryDate();
=======
				single_createattr_AbhraincOrder_expectedDeliveryDate();
>>>>>>> sujan
			
				single_createattr_GoogleProductSearch_catalogVersion();
			
				single_createattr_ApparelProduct_genders();
			
				single_createattr_ApparelStyleVariantProduct_style();
			
				single_createattr_ApparelStyleVariantProduct_swatchColors();
			
				single_createattr_ApparelSizeVariantProduct_size();
			
				single_createattr_ElectronicsColorVariantProduct_color();
			
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
				single_createattr_Order_AbhraincOrderStatus();
			
				single_createattr_User_isEmailActivated();
			
>>>>>>> sujan
=======
=======
>>>>>>> praneeth
				single_createattr_Consignment_trackingEmailSent();
			
				single_createattr_OrderThresholdPercentageDiscount_thresholdTotals();
			
				single_createattr_OrderThresholdPercentageDiscount_qualifyingCount();
			
				single_createattr_OrderThresholdPercentageDiscount_percentageDiscount();
			
				single_createattr_OrderThresholdPercentageDiscount_messageFired();
			
				single_createattr_OrderThresholdPercentageDiscount_messageCouldHaveFired();
			
				single_createattr_User_isEmailActivated();
			
				single_createattr_Order_orderExpectedDeliveryDate();
			
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> sujan
=======
				single_createattr_Consignment_isDeliveryEmailSent();
			
>>>>>>> sujan
=======
>>>>>>> praneeth

	}

	
<<<<<<< HEAD
	public void single_createattr_Order_expectedDeliveryDate() throws JaloBusinessException
=======
	public void single_createattr_AbhraincOrder_expectedDeliveryDate() throws JaloBusinessException
>>>>>>> sujan
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
<<<<<<< HEAD
					"Order", 
=======
					"AbhraincOrder", 
>>>>>>> sujan
					"expectedDeliveryDate",  
					null,
					"java.util.Date",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_GoogleProductSearch_catalogVersion() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"GoogleProductSearch", 
					"catalogVersion",  
					null,
					"catalogVersion",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ApparelProduct_genders() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ApparelProduct", 
					"genders",  
					null,
					"GenderList",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ApparelStyleVariantProduct_style() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ApparelStyleVariantProduct", 
					"style",  
					null,
					"localized:java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					"VariantAttributeDescriptor",
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ApparelStyleVariantProduct_swatchColors() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ApparelStyleVariantProduct", 
					"swatchColors",  
					null,
					"SwatchColorSet",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ApparelSizeVariantProduct_size() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ApparelSizeVariantProduct", 
					"size",  
					null,
					"localized:java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					"VariantAttributeDescriptor",
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_ElectronicsColorVariantProduct_color() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"ElectronicsColorVariantProduct", 
					"color",  
					null,
					"localized:java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					"VariantAttributeDescriptor",
					sqlColumnDefinitions
				);
			
	}
	
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
	public void single_createattr_Order_AbhraincOrderStatus() throws JaloBusinessException
=======
	public void single_createattr_Consignment_trackingEmailSent() throws JaloBusinessException
>>>>>>> sujan
=======
	public void single_createattr_Consignment_trackingEmailSent() throws JaloBusinessException
>>>>>>> praneeth
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"Consignment", 
					"trackingEmailSent",  
					null,
					"java.lang.Boolean",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.PRIMITIVE_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_OrderThresholdPercentageDiscount_thresholdTotals() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"OrderThresholdPercentageDiscount", 
					"thresholdTotals",  
					null,
					"PromotionPriceRowCollectionType",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.PARTOF_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_OrderThresholdPercentageDiscount_qualifyingCount() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"OrderThresholdPercentageDiscount", 
					"qualifyingCount",  
					null,
					"java.lang.Integer",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_OrderThresholdPercentageDiscount_percentageDiscount() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"OrderThresholdPercentageDiscount", 
					"percentageDiscount",  
					null,
					"java.lang.Double",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_OrderThresholdPercentageDiscount_messageFired() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = new HashMap();
						
							sqlColumnDefinitions.put(
								"oracle",
								"varchar2(4000)"
							);
						
							sqlColumnDefinitions.put(
								"mysql",
								"text"
							);
						
							sqlColumnDefinitions.put(
								"sqlserver",
								"nvarchar(max)"
							);
						
							sqlColumnDefinitions.put(
								"hsqldb",
								"LONGVARCHAR"
							);
						
							sqlColumnDefinitions.put(
								de.hybris.platform.persistence.property.PersistenceManager.NO_DATABASE,
								"varchar"
							);
						
				createPropertyAttribute(
					"OrderThresholdPercentageDiscount", 
					"messageFired",  
					null,
					"localized:java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_OrderThresholdPercentageDiscount_messageCouldHaveFired() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = new HashMap();
						
							sqlColumnDefinitions.put(
								"oracle",
								"varchar2(4000)"
							);
						
							sqlColumnDefinitions.put(
								"mysql",
								"text"
							);
						
							sqlColumnDefinitions.put(
								"sqlserver",
								"nvarchar(max)"
							);
						
							sqlColumnDefinitions.put(
								"hsqldb",
								"LONGVARCHAR"
							);
						
							sqlColumnDefinitions.put(
								de.hybris.platform.persistence.property.PersistenceManager.NO_DATABASE,
								"varchar"
							);
						
				createPropertyAttribute(
					"OrderThresholdPercentageDiscount", 
					"messageCouldHaveFired",  
					null,
					"localized:java.lang.String",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
	public void single_createattr_User_isEmailActivated() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"User", 
					"isEmailActivated",  
					null,
					"java.lang.Boolean",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> sujan
=======
=======
>>>>>>> praneeth
	public void single_createattr_Order_orderExpectedDeliveryDate() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"Order", 
					"orderExpectedDeliveryDate",  
					null,
					"java.util.Date",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> sujan
=======
	public void single_createattr_Consignment_isDeliveryEmailSent() throws JaloBusinessException
	{
		
						Map sqlColumnDefinitions = null;
					
				createPropertyAttribute(
					"Consignment", 
					"isDeliveryEmailSent",  
					null,
					"java.lang.Boolean",
					de.hybris.platform.jalo.type.AttributeDescriptor.READ_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.WRITE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.OPTIONAL_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.REMOVE_FLAG|de.hybris.platform.jalo.type.AttributeDescriptor.SEARCH_FLAG,
					null,
					sqlColumnDefinitions
				);
			
	}
	
>>>>>>> sujan
=======
>>>>>>> praneeth


	/**
	 * Generated by hybris Platform.
	 */
	@Override
	protected final void performCreateObjects( final ManagerEJB manager, Map params ) throws JaloBusinessException
	{
		// performCreateObjects
	
	
		createEnumerationValues(
			"SwatchColorEnum",
			true,
			Arrays.asList( new String[] {
			
				"BLACK",
				"BLUE",
				"BROWN",
				"GREEN",
				"GREY",
				"ORANGE",
				"PINK",
				"PURPLE",
				"RED",
				"SILVER",
				"WHITE",
				"YELLOW"
			} )
		);
	
<<<<<<< HEAD
<<<<<<< HEAD
=======
		createEnumerationValues(
			"ConsignmentStatus",
			true,
			Arrays.asList( new String[] {
			
				"ACCEPTED",
				"READYFORDISPATCH",
				"PICKED",
				"DISPATCHED",
				"DELIVERED"
			} )
		);
	
>>>>>>> praneeth
				{
				Map customPropsMap = new HashMap();
				
				changeMetaType(
					"Order",
=======
		createEnumerationValues(
			"ConsignmentStatus",
			true,
			Arrays.asList( new String[] {
			
				"ACCEPTED",
				"READYFORDISPATCH",
				"PICKED",
				"DISPATCHED",
				"DELIVERED"
			} )
		);
	
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"AbhraincOrder",
					false,
					true,
					true,
>>>>>>> sujan
					null,
					customPropsMap
				);
				}
			
<<<<<<< HEAD
			single_setAttributeProperties_Order_expectedDeliveryDate();
=======
			single_setAttributeProperties_AbhraincOrder_expectedDeliveryDate();
>>>>>>> sujan
		
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"GoogleProductSearch",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_GoogleProductSearch_catalogVersion();
		
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"ApparelProduct",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_ApparelProduct_genders();
		
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"ApparelStyleVariantProduct",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_ApparelStyleVariantProduct_style();
		
			single_setAttributeProperties_ApparelStyleVariantProduct_swatchColors();
		
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"ApparelSizeVariantProduct",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_ApparelSizeVariantProduct_size();
		
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"ElectronicsColorVariantProduct",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_ElectronicsColorVariantProduct_color();
		
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> praneeth
				{
				Map customPropsMap = new HashMap();
				
				changeMetaType(
					"Consignment",
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_Consignment_trackingEmailSent();
		
				{
				Map customPropsMap = new HashMap();
				
				setItemTypeProperties(
					"OrderThresholdPercentageDiscount",
					false,
					true,
					true,
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_OrderThresholdPercentageDiscount_thresholdTotals();
		
			single_setAttributeProperties_OrderThresholdPercentageDiscount_qualifyingCount();
		
			single_setAttributeProperties_OrderThresholdPercentageDiscount_percentageDiscount();
		
			single_setAttributeProperties_OrderThresholdPercentageDiscount_messageFired();
		
			single_setAttributeProperties_OrderThresholdPercentageDiscount_messageCouldHaveFired();
		
				{
				Map customPropsMap = new HashMap();
				
				changeMetaType(
					"User",
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_User_isEmailActivated();
		
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> sujan
=======
=======
>>>>>>> praneeth
				{
				Map customPropsMap = new HashMap();
				
				changeMetaType(
					"Order",
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_Order_orderExpectedDeliveryDate();
		
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> sujan
=======
				{
				Map customPropsMap = new HashMap();
				
				changeMetaType(
					"Consignment",
					null,
					customPropsMap
				);
				}
			
			single_setAttributeProperties_Consignment_isDeliveryEmailSent();
		
>>>>>>> sujan
=======
>>>>>>> praneeth
				setDefaultProperties(
					"GenderList",
					true,
					true,
					null
				);
			
				setDefaultProperties(
					"SwatchColorSet",
					true,
					true,
					null
				);
			
				setDefaultProperties(
					"SwatchColorEnum",
					true,
					true,
					null
				);
			
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
				setDefaultProperties(
					"AbhraincOrderStatus",
					true,
					true,
=======
				changeMetaType(
					"ConsignmentStatus",
					null,
>>>>>>> sujan
					null
				);
			
>>>>>>> sujan
=======
				changeMetaType(
					"ConsignmentStatus",
					null,
					null
				);
			
>>>>>>> praneeth
	}


		
<<<<<<< HEAD
						public void single_setAttributeProperties_Order_expectedDeliveryDate() throws JaloBusinessException
=======
						public void single_setAttributeProperties_AbhraincOrder_expectedDeliveryDate() throws JaloBusinessException
>>>>>>> sujan
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
<<<<<<< HEAD
								"Order", 
=======
								"AbhraincOrder", 
>>>>>>> sujan
								"expectedDeliveryDate",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
<<<<<<< HEAD
						public void single_setAttributeProperties_ApparelProduct_genders() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ApparelProduct", 
								"genders",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ApparelStyleVariantProduct_style() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ApparelStyleVariantProduct", 
								"style",
								false, 
								null,
								null,
								null,
								true,
								true,
								"VariantAttributeDescriptor",
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ApparelStyleVariantProduct_swatchColors() throws JaloBusinessException
<<<<<<< HEAD
=======
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ApparelStyleVariantProduct", 
								"swatchColors",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ApparelSizeVariantProduct_size() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ApparelSizeVariantProduct", 
								"size",
								false, 
								null,
								null,
								null,
								true,
								true,
								"VariantAttributeDescriptor",
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ElectronicsColorVariantProduct_color() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ElectronicsColorVariantProduct", 
								"color",
								false, 
								null,
								null,
								null,
								true,
								true,
								"VariantAttributeDescriptor",
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_Consignment_trackingEmailSent() throws JaloBusinessException
=======
						public void single_setAttributeProperties_GoogleProductSearch_catalogVersion() throws JaloBusinessException
>>>>>>> naresh
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
<<<<<<< HEAD
								"Consignment", 
								"trackingEmailSent",
								false, 
								false,
								"false",
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_OrderThresholdPercentageDiscount_thresholdTotals() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"OrderThresholdPercentageDiscount", 
								"thresholdTotals",
=======
								"GoogleProductSearch", 
								"catalogVersion",
>>>>>>> naresh
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_OrderThresholdPercentageDiscount_qualifyingCount() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"OrderThresholdPercentageDiscount", 
								"qualifyingCount",
								false, 
								Integer.valueOf(2),
								"Integer.valueOf(2)",
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_OrderThresholdPercentageDiscount_percentageDiscount() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"OrderThresholdPercentageDiscount", 
								"percentageDiscount",
								false, 
								Double.valueOf(10.0D),
								"Double.valueOf(10.0D)",
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_OrderThresholdPercentageDiscount_messageFired() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"OrderThresholdPercentageDiscount", 
								"messageFired",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_OrderThresholdPercentageDiscount_messageCouldHaveFired() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"OrderThresholdPercentageDiscount", 
								"messageCouldHaveFired",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_User_isEmailActivated() throws JaloBusinessException
<<<<<<< HEAD
>>>>>>> sujan
=======
>>>>>>> praneeth
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"User", 
								"isEmailActivated",
								false, 
<<<<<<< HEAD
<<<<<<< HEAD
								null,
								null,
								null,
								true,
								true,
								"VariantAttributeDescriptor",
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_ElectronicsColorVariantProduct_color() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"ElectronicsColorVariantProduct", 
								"color",
								false, 
								null,
								null,
								null,
								true,
								true,
								"VariantAttributeDescriptor",
=======
=======
>>>>>>> praneeth
								java.lang.Boolean.FALSE,
								"java.lang.Boolean.FALSE",
								null,
								true,
								true,
								null,
<<<<<<< HEAD
>>>>>>> sujan
=======
>>>>>>> praneeth
								customPropsMap,
								null
							);
						}
					
						public void single_setAttributeProperties_Order_orderExpectedDeliveryDate() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"Order", 
								"orderExpectedDeliveryDate",
								false, 
								null,
								null,
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
<<<<<<< HEAD
						public void single_setAttributeProperties_Consignment_isDeliveryEmailSent() throws JaloBusinessException
						{
							
							
							
							Map customPropsMap = new HashMap();
							
							setAttributeProperties(
								"Consignment", 
								"isDeliveryEmailSent",
								false, 
								java.lang.Boolean.FALSE,
								"java.lang.Boolean.FALSE",
								null,
								true,
								true,
								null,
								customPropsMap,
								null
							);
						}
					
=======
>>>>>>> praneeth
}

	